package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.port.PostSearchPort;
import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostSearchService implements PostSearchUsecase {
    private static final int PAGE_SIZE = 5;
    private final PostSearchPort postSearchPort;
    private final PostResolvingHelpUsecase postResolvingHelpUsecase;
    @Override
    public List<ResolvedPost> getSearchResultByKeyword(String keyword, int pageNumber) {
        // ES에서 검색하여 postIds를 가져옴
        List<Long> postIds = postSearchPort.searchPostIdsKeyword(keyword, pageNumber, PAGE_SIZE);
        // 가져온 postIds를 이용하여 resolved post를 가져옴
        return postResolvingHelpUsecase.resolvePostsByIds(postIds);
    }
}
