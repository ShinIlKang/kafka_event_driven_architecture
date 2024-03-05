package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.inspectedPost.InspectedPost;

public interface PostIndexingUsecase {
    void save(InspectedPost post);
    void delete(Long postId);
}
