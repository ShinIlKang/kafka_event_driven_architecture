package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.port.OriginalPostMessageProducePort;
import com.fastcampus.kafkahandson.ugc.port.PostPort;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostCreateService implements PostCreateUsecase {

    private final PostPort postPort;
    private final OriginalPostMessageProducePort originalPostMessageProducePort;
    @Transactional
    @Override
    public Post create(Request request) {
        Post post =  postPort.save(
            Post.generate(
                request.getUserId(),
                request.getTitle(),
                request.getContent(),
                request.getCategoryId()
        ));

        originalPostMessageProducePort.sendCreateMessage(post);

        return post;
    }
}
