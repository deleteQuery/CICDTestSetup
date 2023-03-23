package com.training.queue.pojo;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QueueOperationRequest {

    @NonNull
    private String queueName;

    @NonNull
    private Map<String, Object> payload;

    @NonNull
    private String producerId;
}
