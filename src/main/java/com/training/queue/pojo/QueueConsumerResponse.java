package com.training.queue.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueConsumerResponse {
    private String id;
    private String consumerName;
    private String queueId;
    private String queueName;
    private Long offset;
    private Date lastUpdatedAt;
    private String callBackURL;
}
