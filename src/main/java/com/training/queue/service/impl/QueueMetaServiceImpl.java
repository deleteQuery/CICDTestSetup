package com.training.queue.service.impl;

import com.training.queue.exception.InvalidDataException;
import com.training.queue.exception.NoDataFoundException;
import com.training.queue.model.QueueData;
import com.training.queue.model.QueueMetaData;
import com.training.queue.pojo.QueueMetaRequest;
import com.training.queue.pojo.QueueMetaResponse;
import com.training.queue.repository.QueueConsumerRepo;
import com.training.queue.repository.QueueDataRepo;
import com.training.queue.repository.QueueMetaDataRepo;
import com.training.queue.service.IQueueMetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.training.queue.utilities.ParserUtils.*;

@Service
@Slf4j
public class QueueMetaServiceImpl implements IQueueMetaService {

    QueueMetaDataRepo queueMetaDataRepo;
    QueueDataRepo queueDataRepo;
    QueueConsumerRepo queueConsumerRepo;

    public QueueMetaServiceImpl(QueueMetaDataRepo queueMetaDataRepo,
                                QueueDataRepo queueDataRepo,
                                QueueConsumerRepo queueConsumerRepo) {
        this.queueMetaDataRepo = queueMetaDataRepo;
        this.queueDataRepo = queueDataRepo;
        this.queueConsumerRepo = queueConsumerRepo;
    }

    public QueueMetaResponse createQueue(final QueueMetaRequest queueMetaRequest) throws InvalidDataException {
        QueueMetaData queueMetaData = queueMetaDataRepo.findByQueueName(queueMetaRequest.getQueueName());
        if(Objects.isNull(queueMetaData)) {
            queueMetaData = queueMetaDataRepo.save(getQueueMetaData(queueMetaRequest));
            return getQueueMetaResponse(queueMetaData);
        } else {
            log.error("[createQueue] Queue already exists - {}", queueMetaRequest);
            throw new InvalidDataException("Queue already exists");
        }
    }

    public QueueMetaResponse getQueue(final String queueName) throws NoDataFoundException {
        QueueMetaData queueMetaData = queueMetaDataRepo.findByQueueName(queueName);
        if(Objects.nonNull(queueMetaData)) {
            log.info("[getQueue] {}", queueMetaData);
            return getQueueMetaResponse(queueMetaData);
        } else {
            log.error("[getQueue] No queue regsitered with this name - {}", queueName);
            throw new NoDataFoundException("No queue regsitered with this name");
        }
    }

    @Transactional
    public void deleteQueue(final String queueName) throws NoDataFoundException {
        QueueMetaData queueMetaData = queueMetaDataRepo.findByQueueName(queueName);
        if(Objects.nonNull(queueMetaData)) {
            log.info("[getQueue] {}", queueMetaData);
            queueMetaDataRepo.deleteById(queueMetaData.getId());
            queueDataRepo.deleteByQueueId(queueMetaData.getId());
            queueConsumerRepo.deleteByQueueId(queueMetaData.getId());
            log.info("Queue data deleted with meta data: {}", queueName);
        } else {
            log.error("[getQueue] No queue regsitered with this name - {}", queueName);
            throw new NoDataFoundException("No queue regsitered with this name");
        }
    }

    public List<QueueData> getAllPayloadsOfAQueue(final String queueName) {
        return queueDataRepo.findByQueueName(queueName);
    }
}
