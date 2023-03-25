package com.training.queue.service.impl;

import com.training.queue.exception.InvalidDataException;
import com.training.queue.exception.NoDataFoundException;
import com.training.queue.model.QueueConsumer;
import com.training.queue.model.QueueMetaData;
import com.training.queue.pojo.QueueConsumerRequest;
import com.training.queue.pojo.QueueConsumerResponse;
import com.training.queue.pojo.QueueMetaResponse;
import com.training.queue.repository.QueueConsumerRepo;
import com.training.queue.repository.QueueMetaDataRepo;
import com.training.queue.service.IQueueConsumerService;
import com.training.queue.utilities.ParserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
public class QueueConsumerImpl implements IQueueConsumerService {

    QueueConsumerRepo queueConsumerRepo;
    QueueMetaDataRepo queueMetaDataRepo;

    public QueueConsumerImpl(QueueConsumerRepo queueConsumerRepo, QueueMetaDataRepo queueMetaDataRepo) {
        this.queueConsumerRepo = queueConsumerRepo;
        this.queueMetaDataRepo = queueMetaDataRepo;
    }

    public QueueConsumerResponse registerQueueConsumer(final QueueConsumerRequest queueConsumerRequest) throws InvalidDataException, NoDataFoundException {
        QueueConsumer queueConsumer =
                queueConsumerRepo.findByConsumerName(queueConsumerRequest.getConsumerName());
        log.info("[registerQueueConsumer] Queue register request for " +
                "request: {} and queueConsumer fetch: {}", queueConsumerRequest, queueConsumer);
        if (Objects.isNull(queueConsumer)) {
            QueueMetaData queueMetaData =
                    queueMetaDataRepo.findByQueueName(queueConsumerRequest.getQueueName());
            log.info("[registerQueueConsumer] Queue register request for " +
                    "request: {} and QueueMetaData - {}", queueConsumerRequest, queueMetaData);
            if(Objects.nonNull(queueMetaData)) {
                QueueConsumer consumer = queueConsumerRepo.save(ParserUtils.getQueueConsumer(queueConsumerRequest,
                        queueMetaData.getId()));
                log.info("[registerQueueConsumer] Queue register request for " +
                        "request: {} and queueConsumer fetch after save: {}", queueConsumerRequest,
                        consumer);
                return ParserUtils.getQueueConsumerResponse(consumer);
            } else {
                log.error("[registerQueueConsumer] No queue registered with the system for " +
                        "request: {}", queueConsumerRequest);
                throw new NoDataFoundException("No queue registered with the system");
            }
        } else {
            log.error("[registerQueueConsumer] Queue consumer already registered with the system " +
                    "for request: {}", queueConsumerRequest);
            throw new InvalidDataException("Queue consumer already registered with the system");
        }
    }

    @Transactional
    public void deleteQueueConsumer(final String consumerName) throws NoDataFoundException {
        QueueConsumer queueConsumer = queueConsumerRepo.findByConsumerName(consumerName);
        log.info("[deleteQueueConsumer] Fetch queue consumer request for " +
                "consumer: {} and queueConsumer fetch: {}", consumerName, queueConsumer);
        if(Objects.nonNull(queueConsumer)) {
            queueConsumerRepo.deleteById(queueConsumer.getId());
        } else {
            log.error("[deleteQueueConsumer] Queue consumer does not exist: {}", consumerName);
            throw new NoDataFoundException("Queue consumer does not exist");
        }

    }

    public QueueConsumerResponse getQueueConsumer(final String consumerName) throws NoDataFoundException {
        QueueConsumer queueConsumer = queueConsumerRepo.findByConsumerName(consumerName);
        log.info("[getQueueConsumer] Fetch queue consumer request for " +
                "consumer: {} and queueConsumer fetch: {}", consumerName, queueConsumer);
        if(Objects.nonNull(queueConsumer)) {
            return ParserUtils.getQueueConsumerResponse(queueConsumer);
        } else {
            log.error("[getQueueConsumer] Queue consumer response for " +
                    "consumer: {} does not exist", consumerName);
            throw new NoDataFoundException("Queue consumer does not exist");
        }
    }
}
