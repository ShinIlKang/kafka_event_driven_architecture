package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.port.OriginalPostMessageProducePort;
import com.fastcampus.kafkahandson.ugc.port.PostPort;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
@DisplayName("PostCreateService 테스트")
@ExtendWith(MockitoExtension.class)
public class PostCreateServiceTest {
    @Mock
    private OriginalPostMessageProducePort originalPostMessageProducePort;

    @Mock
    private PostPort postPort;

    @InjectMocks
    private PostCreateService postCreateService;

    @Test
    @DisplayName("포스트를 생성하고 Kafka에 메시지를 전송하는 메소드를 호출한다.")
    void createTest() {
        PostCreateUsecase.Request request = new PostCreateUsecase.Request(1L, "title", "content", 1L);
        Post expectedPost = new Post(
                1L
                , "title"
                , "content"
                , 1L
                , 1L
                , null
                , null
                , null
        );

        when(postPort.save(any(Post.class))).thenReturn(expectedPost);
        Post result = postCreateService.create(request);
        assertEquals(expectedPost, result);
        verify(postPort).save(any(Post.class));
        verify(originalPostMessageProducePort).sendCreateMessage(expectedPost);
    }
}
