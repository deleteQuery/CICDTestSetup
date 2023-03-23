package com.training.queue.pojo;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

import static com.training.queue.constant.ModelConstant.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QueueConsumerRequest {

    @NonNull
    private String consumerName;

    @NonNull
    private String queueName;

    @NonNull
    private String callBackURL;
}
