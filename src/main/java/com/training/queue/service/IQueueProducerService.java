package com.training.queue.service;

import com.training.queue.exception.NoDataFoundException;
import com.training.queue.pojo.QueueOperationRequest;
import com.training.queue.pojo.QueueOperationResponse;

public interface IQueueProducerService {
    QueueOperationResponse pushDataToQueue(final QueueOperationRequest operationRequest) throws NoDataFoundException;
}
