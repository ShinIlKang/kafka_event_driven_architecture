package com.fastcampus.kafkahandson.ugc.port;

import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;

import java.util.List;

public interface ResolvedPostCachePort {
    // cache set
    void set(ResolvedPost resolvedPost);
    // cache get
    ResolvedPost get(Long postId);
    void delete(Long postId);
    List<ResolvedPost> multiGet(List<Long> postIds);
}
