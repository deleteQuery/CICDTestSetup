package com.training.queue.pojo;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QueueMetaRequest {

    @NonNull
    private String queueName;

    @NonNull
    private String userId;

//    private Long retentionPeriod; @todo as an extension support
//    private QueueSchema queueSchema; @todo for future extensions
//    private Boolean isSchemaValidationEnabled; @todo depending upon QueueSchema
//    private String version; @todo depending upon the evolutio of schema support
}
