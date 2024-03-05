package com.fastcampus.kafkahandson.ugc.port;

import com.fastcampus.kafkahandson.ugc.inspectedPost.InspectedPost;

import java.util.List;

public interface PostSearchPort {
    // save
    void indexPost(InspectedPost post);
    // delete
    void deletePost(Long id);
    // es에서 Keyword로 검색하여 postId 목록을 리턴
    List<Long> searchPostIdsKeyword(String keyword, int pageNumber, int pageSize);
}
