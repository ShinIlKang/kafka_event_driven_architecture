package com.fastcampus.kafkahandson.ugc.port;

import com.fastcampus.kafkahandson.ugc.inspectedPost.AutoInspectionResult;
import com.fastcampus.kafkahandson.ugc.post.model.Post;

public interface PortAutoInspectPort {
    AutoInspectionResult inspect(Post post, String categoryName);
}
