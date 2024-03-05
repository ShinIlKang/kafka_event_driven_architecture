package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.port.MetadataPort;
import com.fastcampus.kafkahandson.ugc.port.PostPort;
import com.fastcampus.kafkahandson.ugc.port.ResolvedPostCachePort;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostResolvingHelpService implements PostResolvingHelpUsecase {

    private final PostPort postPort;
    private final MetadataPort metadataPort;
    private final ResolvedPostCachePort resolvedPostCachePort;

    @Override
    public ResolvedPost resolvePostById(Long postId) {
        ResolvedPost resolvedPost = resolvedPostCachePort.get(postId);
        if (resolvedPost != null) {
            return resolvedPost;
        }
        Post post = postPort.findById(postId);
        if (post != null) {
            resolvedPost = this.resolvedPost(post);
        }
        return resolvedPost;
    }

    @Override
    public List<ResolvedPost> resolvePostsByIds(List<Long> postIds) { // redis muti get으로 개선
        if (postIds == null || postIds.isEmpty()) {
            return List.of();
        }
        List<ResolvedPost> resolvedPostCaches = new ArrayList<>();
        resolvedPostCaches.addAll(resolvedPostCachePort.multiGet(postIds));
        // 유효기간이 7일이고 추가된 데이터가 있으면 모든 데이터를 가져올 수 없기 때문에 합치는 작업
        // 1. Cache에 없는 postId 목록을 가져온다.
        List<Long> missingPostIds = postIds.stream()
                .filter(postId -> resolvedPostCaches.stream().noneMatch(resolvedPost -> resolvedPost.getId().equals(postId))).toList();
        // 2. 1에서 가져온 postId 목록으로 원천 데이터에서 조회하여 가져온다.
        List<Post> missingPosts = postPort.listByIds(missingPostIds);
        // 3. ResolvedPost 형으로 변환하고 추가
        List<ResolvedPost> missingResolvedPosts = missingPosts.stream()
                .map(this::resolvedPost)
                .filter(Objects::nonNull)
                .toList();
        resolvedPostCaches.addAll(missingResolvedPosts);
        // 4. postId를 키로 두고 맵으로 만든 후 순서 정렬
        Map<Long, ResolvedPost> resolvedPostMap = resolvedPostCaches.stream()
                .collect(Collectors.toMap(ResolvedPost::getId, Function.identity()));
        return postIds.stream()
                .map(resolvedPostMap::get)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public void resolvePostAndSave(Post post) {
        ResolvedPost resolvedPost = this.resolvedPost(post);
        if (resolvedPost != null) {
            resolvedPostCachePort.set(resolvedPost);
        }
    }

    @Override
    public void deleteResolvePost(Long postId) {
        resolvedPostCachePort.delete(postId);
    }

    private ResolvedPost resolvedPost(Post post) {
        if (post == null ) return null;
        ResolvedPost resolvedPost = null;
        String userName = metadataPort.getUserNameByUserId(post.getUserId());
        String categoryName = metadataPort.getCategoryNameByCategoryId(post.getCategoryId());
        if (userName != null && categoryName != null) {
            resolvedPost = ResolvedPost.generate(
                    post,
                    userName,
                    categoryName
            );
            resolvedPostCachePort.set(resolvedPost);
        }

        return resolvedPost;
    }
}
