package com.fastcampus.kafkahandson.ugc.chatgpt;

import com.fastcampus.kafkahandson.ugc.CustomObjectMapper;
import com.fastcampus.kafkahandson.ugc.inspectedPost.AutoInspectionResult;
import com.fastcampus.kafkahandson.ugc.port.PortAutoInspectPort;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PortAutoInspectAdapter implements PortAutoInspectPort {

    private final ChatGptClient chatGptClient;

    private final CustomObjectMapper objectMapper = new CustomObjectMapper();

    @Override
    public AutoInspectionResult inspect(Post post, String categoryName) {
        // ChatGpt에게 할말을 적는다.
        String contentString = buildContentString(post, categoryName);
        // ChatGpt에게 어떻게 대답을 해야할지 지침을 준다.
        ChatGptClient.ChatPolicy chatPolicy = new ChatGptClient.ChatPolicy(
                AutoInspectionPolicy.INSPECTION_INSTRUCTION,
                AutoInspectionPolicy.EXAMPLE_CONTENT,
                AutoInspectionPolicy.EXAMPLE_INSPECTION_RESULT
        );
        // ChatGpt에게 검수를 시켜서 결과를 얻는다.
        String resultString = chatGptClient.getResultForContentWithPolicy(
                contentString,
                chatPolicy
        );

        try {
            return objectMapper.readValue(resultString, AutoInspectionResult.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildContentString(Post post, String categoryName) {
        return String.format(
                "[%s] %s - %s",
                categoryName,
                post.getTitle(),
                post.getContent()
        );
    }

    static class AutoInspectionPolicy {
        private static final String INSPECTION_INSTRUCTION =
                "The task you need to accomplish is to return two items ('status' and 'tags') in JSON format. " +
                        "The information I will provide will be in the format '[Post category] Post content.' " +
                        "Then, if the content of the post aligns well with the meaning or theme of the post category, " +
                        "fill the 'status' field with the string 'GOOD.' " +
                        "If the meaning or theme appears unrelated, " +
                        "fill the 'status' field with the string 'BAD.' " +
                        "Additionally, extract and compile a list of up to 5 keywords " +
                        "that seem most important in the post content and populate the 'tags' field with them.";
        private static final String EXAMPLE_CONTENT =
                "[Health] Reps and Muscle Size - To increase muscle size, " +
                        "it is considered most ideal to exercise with the maximum weight " +
                        "that allows 8 to 12 repetitions per set.";
        private static final String EXAMPLE_INSPECTION_RESULT =
                "{\"status\":\"GOOD\",\"tags\":[\"muscle\", \"weight\", \"repetitions\"]}";
    }
}
