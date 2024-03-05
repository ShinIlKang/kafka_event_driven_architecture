package com.fastcampus.kafkahandson.ugc.inspectedPost;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutoInspectionResult {
    private String status; // Good or Bad;
    private String[] tags;
}
