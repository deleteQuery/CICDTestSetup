package com.training.queue.service;

import com.training.queue.exception.InvalidDataException;
import com.training.queue.exception.NoDataFoundException;
import com.training.queue.model.QueueData;
import com.training.queue.pojo.QueueMetaRequest;
import com.training.queue.pojo.QueueMetaResponse;

import java.util.List;

public interface IQueueMetaService {

    QueueMetaResponse createQueue(final QueueMetaRequest queueMetaRequest) throws InvalidDataException;

    QueueMetaResponse getQueue(final String queueName) throws NoDataFoundException;

    List<QueueData> getAllPayloadsOfAQueue(final String queueName);

    void deleteQueue(final String queueName) throws NoDataFoundException;
}
