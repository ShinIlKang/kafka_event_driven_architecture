package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.port.MetadataPort;
import com.fastcampus.kafkahandson.ugc.port.SubscribingPostPort;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribingPostAddtoInboxService implements SubscribingPostAddtoInboxUsecase {

    private final SubscribingPostPort subscribingPostPort;
    private final MetadataPort metadataPort;
    @Override
    public void saveSubscribingInboxPost(Post post) {
        List<Long> followerUserIds = metadataPort.listFollowerIdsByUserId(post.getUserId()); // 컨텐츠를 작성한 유저아디를 가지고 구독자 아이디들을 가져온다.
        subscribingPostPort.addPostToFollowerInboxes(post, followerUserIds);
    }
}
