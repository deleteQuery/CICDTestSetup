package com.training.queue.repository;

import com.training.queue.model.QueueData;
import com.training.queue.model.QueueMetaData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.training.queue.constant.ModelConstant.*;

@Repository
public interface QueueDataRepo extends MongoRepository<QueueData, String> {

    @Query("{ '"+ QUEUE_NAME +"' : ?0 }")
    List<QueueData> findByQueueName(final String queueName);

    @Query("{ '"+ QUEUE_NAME +"' : ?0, `" + OFFSET +"` : {`$gt` : ?1} }")
    List<QueueData> findByQueueNameAndOffsetGreaterThan(final String queueName, final Long offset);

    @Query("{ '"+ QUEUE_ID +"' : ?0 }")
    void deleteByQueueId(final String queueId);

}
