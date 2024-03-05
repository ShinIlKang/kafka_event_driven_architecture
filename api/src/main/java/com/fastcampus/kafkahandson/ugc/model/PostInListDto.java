package com.fastcampus.kafkahandson.ugc.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostInListDto {
    private final Long id;
    private final String title;
    private final String userName;
    private final LocalDateTime createdAt;
}
