package com.training.queue.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import static com.training.queue.constant.ModelConstant.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class QueueDataResponse implements Serializable {

    @JsonProperty(value = MESSAGE_ID)
    private String id;

    @JsonProperty(value = QUEUE_ID)
    private String queueId;

    @JsonProperty(value = QUEUE_NAME)
    private String queueName;

    @JsonProperty(value = OFFSET)
    private Long offset;

    @JsonProperty(value = DATA)
    private Map<String, Object> data;

    @JsonProperty(value = CREATED_AT)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;
}
