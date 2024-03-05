package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.port.OriginalPostMessageProducePort;
import com.fastcampus.kafkahandson.ugc.port.PostPort;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostDeleteService implements PostDeleteUsecase {
    private final PostPort postPort;
    private final OriginalPostMessageProducePort originalPostMessageProducePort;
    @Override
    public Post delete(Request request) {
        Post post = postPort.findById(request.getPostId());
        if (post == null) return null;
        post.delete();
        Post deletedPost = postPort.save(post); // soft delete (deleteAt)
        originalPostMessageProducePort.sendDeleteMessage(post.getId());
        return deletedPost;
    }
}
