package com.fastcampus.kafkahandson.ugc.adapter.originpost;

import com.fastcampus.kafkahandson.ugc.CustomObjectMapper;
import com.fastcampus.kafkahandson.ugc.port.OriginalPostMessageProducePort;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.common.Topic;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OriginalPostMessageProduceAdapter implements OriginalPostMessageProducePort {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CustomObjectMapper customObjectMapper = new CustomObjectMapper();
    @Override
    public void sendCreateMessage(Post post) {
        OriginalPostMessage message = convertToMessage(post.getId(), post, OperationType.CREATE);
        this.sendMessage(message);
    }

    @Override
    public void sendUpdateMessage(Post post) {
        OriginalPostMessage message = convertToMessage(post.getId(), post, OperationType.UPDATE);
        this.sendMessage(message);
    }

    @Override
    public void sendDeleteMessage(Long id) {
        OriginalPostMessage message = convertToMessage(id, null, OperationType.DELETE);
        this.sendMessage(message);
    }

    private OriginalPostMessage convertToMessage(Long id, Post post, OperationType operationType) {
        return new OriginalPostMessage(
                id,
                post == null ? null : new OriginalPostMessage.Payload(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getUserId(),
                        post.getCategoryId(),
                        post.getCreatedAt(),
                        post.getUpdatedAt(),
                        post.getDeletedAt()
                ),
                operationType
        );
    }

    private void sendMessage(OriginalPostMessage message) {
        try {
            kafkaTemplate.send(Topic.ORIGINAL_TOPIC, message.getId().toString(), customObjectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
