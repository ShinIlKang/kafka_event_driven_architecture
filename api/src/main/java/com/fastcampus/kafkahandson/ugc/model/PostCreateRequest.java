package com.fastcampus.kafkahandson.ugc.model;

import lombok.Data;

@Data
public class PostCreateRequest {
    private String title;
    private Long userId;
    private String content;
    private Long categoryId;
}
