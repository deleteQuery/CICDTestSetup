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
@ToString
@Document(collection = QUEUE_METADATA_COLLECTION_NAME)
public class QueueMetaData implements Serializable {

    @Column(name = QUEUE_ID)
    @Id
    private String id;

    @Column(name = QUEUE_NAME)
    private String queueName;

    @Column(name = OFFSET)
    private Long offset;

    @Column(name = CREATED_BY)
    private String createdBy;

    @Column(name = UPDATED_BY)
    private String updatedBy;

    @Column(name = CREATED_AT)
    private Date createdAt;

    @Column(name = UPDATED_AT)
    private Date updatedAt;

//    @Column(name = VERSION)
//    private String version;
//
//    @Column(name = QUEUE_SCHEMA)
//    private QueueSchema queueSchema;
//
//    @Column(name = IS_SCHEMA_VALIDATION_REQUIRED)
//    private Boolean isSchemaValidationEnabled;
}
