package com.fastcampus.kafkahandson.ugc.consumer;

import com.fastcampus.kafkahandson.ugc.CustomObjectMapper;
import com.fastcampus.kafkahandson.ugc.SubscribingPostAddtoInboxUsecase;
import com.fastcampus.kafkahandson.ugc.SubscribingPostRemoveFromInboxUsecase;
import com.fastcampus.kafkahandson.ugc.adapter.common.OperationType;
import com.fastcampus.kafkahandson.ugc.adapter.common.Topic;
import com.fastcampus.kafkahandson.ugc.adapter.inspectedpost.InspectedPostMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ContentSubscribingWorker {
    private final CustomObjectMapper objectMapper = new CustomObjectMapper();

    private final SubscribingPostRemoveFromInboxUsecase subscribingPostRemoveFromInboxUsecase;
    private final SubscribingPostAddtoInboxUsecase subscribingPostAddtoInboxUsecase;

    @KafkaListener(
            topics = {Topic.INSPECTED_POST},
            groupId = "subscribing-post-consumer-group",
            concurrency = "3"
    )
    public void listen(ConsumerRecord<String, String> message) throws JsonProcessingException {
        InspectedPostMessage inspectedPostMessage = objectMapper.readValue(message.value(), InspectedPostMessage.class);
        if (inspectedPostMessage.getOperationType() == OperationType.CREATE) {
            this.handleCreate(inspectedPostMessage);
        } else if (inspectedPostMessage.getOperationType() == OperationType.UPDATE) {
            // DO NOTHING 조회할 때 postId 목록만 가져와서 쓰기 때문에 업데이트 할 게 없음.
            // 리졸빙은 구독 컨텐츠 목록 조회 하기 직전에 함.
            // post 내용이 어떻게 되는 postId 목록만 가져올거라서 업데이트는 안 함.
            // post 내용이 업데이트 했더라도 다시 set해서 조회 할거임
        } else if (inspectedPostMessage.getOperationType() == OperationType.DELETE) {
            this.handleDelete(inspectedPostMessage);
        }
    }

    private void handleCreate(InspectedPostMessage inspectedPostMessage) {
        subscribingPostAddtoInboxUsecase.saveSubscribingInboxPost(inspectedPostMessage.getPayload().getPost());
    }

    private void handleDelete(InspectedPostMessage inspectedPostMessage) {
        subscribingPostRemoveFromInboxUsecase.deleteSubscribingInboxPost(inspectedPostMessage.getId());
    }
}
