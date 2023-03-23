package com.training.queue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import static com.training.queue.constant.ModelConstant.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Document(collection = QUEUE_DATA_COLLECTION_NAME)
public class QueueData implements Serializable {

    @Column(name = MESSAGE_ID)
    @Id
    private String id;

    @Column(name = QUEUE_ID)
    private String queueId;

    @Column(name = QUEUE_NAME)
    @JsonProperty(value = QUEUE_NAME)
    private String queueName;

    @Column(name = OFFSET)
    private Long offset;

    @Column(name = DATA)
    private Map<String, Object> data;

    @Column(name = CREATED_AT)
    private Date createdAt;
}
