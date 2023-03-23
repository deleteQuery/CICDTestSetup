package com.training.queue.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import static com.training.queue.constant.ModelConstant.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = QUEUE_CONSUMER_COLLECTION_NAME)
public class QueueConsumer implements Serializable {

    @Column(name = QUEUE_CONSUMER_ID)
    @Id
    private String id;

    @Column(name = CONSUMER_NAME)
    private String consumerName;

    @Column(name = QUEUE_ID)
    private String queueId;

    @Column(name = QUEUE_NAME)
    private String queueName;

    @Column(name = OFFSET)
    private Long offset;

    @Column(name = UPDATED_AT)
    private Date updatedAt;

    @Column(name = CREATED_AT)
    private Date createdAt;

    @Column(name = CALLBACK_URL)
    private String callBackURL;
}
