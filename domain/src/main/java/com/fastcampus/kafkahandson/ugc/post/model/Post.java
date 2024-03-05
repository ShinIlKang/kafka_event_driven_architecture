package com.fastcampus.kafkahandson.ugc.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 원천 데이터 관리용
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Post {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    /**
     * 업데이트 메소드
     *
     * @param title 제목
     * @param content 내용
     * @param categoryId 카테고리 아이디
     * @return Post
     */
    public Post update(String title, String content, Long categoryId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    /**
     * 삭제 메소드
     *
     * @return Post
     */
    public Post delete() {
        LocalDateTime now = LocalDateTime.now();
        this.deletedAt = now;
        this.updatedAt = now;
        return this;
    }

    /**
     * 지운걸 살려내는 메소드
     *
     * @return Post
     */
    public Post Undelete() {
        this.deletedAt = null;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    public static Post generate(
            Long userId,
            String title,
            String content,
            Long categoryId
    ) {
        LocalDateTime now = LocalDateTime.now();
        return new Post(
                null,
                title,
                content,
                userId,
                categoryId,
                now,
                now,
                null
        );
    }
}
