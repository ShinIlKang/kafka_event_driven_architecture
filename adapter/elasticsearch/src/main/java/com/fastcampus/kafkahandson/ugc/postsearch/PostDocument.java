package com.fastcampus.kafkahandson.ugc.postsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "post-1")
public class PostDocument {
    @Id
    private Long id;
    private String title;
    private String content;
    private String categoryName; // 카테고리 이름 (id x), userName x user id x 작성자 검색을 지원하지 않음
    private List<String> tags;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime indexedAt;
}
