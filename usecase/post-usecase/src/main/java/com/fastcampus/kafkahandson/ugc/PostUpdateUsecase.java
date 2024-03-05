package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.Data;

public interface PostUpdateUsecase {
    Post update(PostUpdateService.Request request);

    @Data
    class Request {
        private final Long postId;
        private final String title;
        private final String content;
        private final Long categoryId;
    }
}
