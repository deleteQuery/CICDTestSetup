package com.training.queue.service;

import com.training.queue.exception.InvalidDataException;
import com.training.queue.exception.NoDataFoundException;
import com.training.queue.pojo.QueueConsumerRequest;
import com.training.queue.pojo.QueueConsumerResponse;

public interface IQueueConsumerService {

    QueueConsumerResponse registerQueueConsumer(final QueueConsumerRequest queueConsumerRequest) throws InvalidDataException, NoDataFoundException;

    QueueConsumerResponse getQueueConsumer(final String consumerName) throws NoDataFoundException;

    void deleteQueueConsumer(final String consumerName) throws NoDataFoundException;
}
