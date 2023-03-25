package com.training.queue.repository;

import com.training.queue.model.QueueMetaData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import static com.training.queue.constant.ModelConstant.QUEUE_NAME;

@Repository
public interface QueueMetaDataRepo extends MongoRepository<QueueMetaData, String> {

    @Query("{ '"+ QUEUE_NAME +"' : ?0 }")
    QueueMetaData findByQueueName(final String queueName);
}
