package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;

import java.util.List;

public interface PostResolvingHelpUsecase {

    ResolvedPost resolvePostById (Long postId); // 상세

    List<ResolvedPost> resolvePostsByIds(List<Long> postIds); // 목록(구독, 검색)

    void resolvePostAndSave(Post post);

    void deleteResolvePost(Long postId);
}
