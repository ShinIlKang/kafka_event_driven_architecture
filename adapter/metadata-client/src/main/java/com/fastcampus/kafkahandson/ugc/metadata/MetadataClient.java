package com.fastcampus.kafkahandson.ugc.metadata;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MetadataClient {

    private final WebClient webClient;

    public CategoryResponse getCategoryById(Long categoryId) {
        return webClient
                .get()
                .uri("/categories/" + categoryId)
                .retrieve()
                .bodyToMono(CategoryResponse.class)
                .block();
    }

    public UserResponse getUserById(Long userId) {
        return webClient
                .get()
                .uri("/users/" + userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
    }

    public List<Long> getFollowerIdsByUserId(Long userId) {
        return webClient
                .get()
                .uri("/followers?followingId=" + userId)
                .retrieve()
                .bodyToFlux(Long.class)
                .collectList()
                .block();
    }

    @Data
    @NoArgsConstructor
    public static class CategoryResponse {
        private Long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    public static class UserResponse {
        private Long id;
        private String email;
        private String name;
    }
}
