package com.training.queue.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueOperationResponse {
    private String queueName;
    private Long offset;
    private String remark;
    private Boolean isSuccessful;
}
