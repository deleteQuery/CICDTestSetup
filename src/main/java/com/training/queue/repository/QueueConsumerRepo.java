package com.training.queue.repository;

import com.training.queue.model.QueueConsumer;
import com.training.queue.model.QueueData;
import com.training.queue.model.QueueMetaData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.training.queue.constant.ModelConstant.*;

@Repository
public interface QueueConsumerRepo extends MongoRepository<QueueConsumer, String> {

    @Query("{ '"+ CONSUMER_NAME +"' : ?0 }")
    QueueConsumer findByConsumerName(final String consumerName);

    @Query("{ '"+ QUEUE_NAME +"' : ?0 }")
    List<QueueConsumer> findByQueueName(final String queueName);

    @Query("{ '"+ QUEUE_ID +"' : ?0 }")
    void deleteByQueueId(final String queueId);
}
