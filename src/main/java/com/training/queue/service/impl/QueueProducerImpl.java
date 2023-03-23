package com.training.queue.service.impl;

import com.training.queue.constant.ModelConstant;
import com.training.queue.exception.NoDataFoundException;
import com.training.queue.model.QueueConsumer;
import com.training.queue.model.QueueData;
import com.training.queue.model.QueueMetaData;
import com.training.queue.pojo.QueueOperationRequest;
import com.training.queue.pojo.QueueOperationResponse;
import com.training.queue.repository.QueueConsumerRepo;
import com.training.queue.repository.QueueDataRepo;
import com.training.queue.repository.QueueMetaDataRepo;
import com.training.queue.service.IConsumerCallback;
import com.training.queue.service.IQueueProducerService;
import com.training.queue.utilities.ParserUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.training.queue.constant.ModelConstant.*;
import static com.training.queue.constant.ModelConstant.OFFSET;

@Service
@Slf4j
public class QueueProducerImpl implements IQueueProducerService {

    QueueMetaDataRepo queueMetaDataRepo;
    QueueDataRepo queueDataRepo;
    MongoTemplate mongoTemplate;
    QueueConsumerRepo queueConsumerRepo;

    public QueueProducerImpl(QueueMetaDataRepo queueMetaDataRepo, QueueDataRepo queueDataRepo,
                             MongoTemplate mongoTemplate, QueueConsumerRepo queueConsumerRepo) {
        this.queueMetaDataRepo = queueMetaDataRepo;
        this.queueDataRepo = queueDataRepo;
        this.mongoTemplate = mongoTemplate;
        this.queueConsumerRepo = queueConsumerRepo;
    }

    public QueueOperationResponse pushDataToQueue(final QueueOperationRequest operationRequest) throws NoDataFoundException {
        QueueMetaData queueMetaData =
                queueMetaDataRepo.findByQueueName(operationRequest.getQueueName());
        log.info("[pushDataToQueue] Pushing data for " +
                "request: {} and QueueMetaData - {}", operationRequest, queueMetaData);
        if(Objects.nonNull(queueMetaData)) {
            // Push data
            Long currentOffset = getLastOffsetOfQueue(operationRequest.getQueueName());
            log.info("[pushDataToQueue] current offset for queue({}) - {}",
                    operationRequest.getQueueName(), currentOffset);
            QueueData currentData = queueDataRepo.save(ParserUtils.getQueueData(operationRequest,
                    queueMetaData.getId(), currentOffset));
            log.info("[pushDataToQueue] Data pushed successfully for " +
                    "request: {} and QueueData fetch after save: {}", operationRequest,
                    currentData);

            // Notify all the consumers
            notifyConsumers(operationRequest, currentData);

            return ParserUtils.getQueueOperationResponse(currentData);
        } else {
            log.error("[pushDataToQueue] No queue registered with the system for " +
                    "request: {}", operationRequest);
            throw new NoDataFoundException("No queue registered with the system");
        }
    }

    /*
     * @todo this should be async processing
     */
    private void notifyConsumers(QueueOperationRequest operationRequest, QueueData currentData) {
        List<QueueConsumer> queueConsumers = queueConsumerRepo.findByQueueName(operationRequest.getQueueName());
        if(!CollectionUtils.isEmpty(queueConsumers)) {
            queueConsumers.forEach(consumer -> {
                try {
                    Long successfulOffset = notifyOneConsumer(consumer, currentData);
                    consumer.setOffset(successfulOffset);
                    queueConsumerRepo.save(consumer);
                    log.info("[NotifyConsumer] consumer offset updated: {}", consumer);
                } catch (Exception exception) {
                    log.error("[NotifyConsumer] Error while notifying consumer for request: {} and data: {} with " +
                            "error: {}", operationRequest, currentData, exception.getMessage());
                }
            });
        } else {
            log.info("Currently no consumers for the queue: {}", operationRequest.getQueueName());
        }
    }

    /*
     * Function to get last offset from DB using findAndModify of MongoDB.
     * But in the future, it should be achieved from accessing some cache for a faster performance.
     */
    private Long getLastOffsetOfQueue(final String queueName) throws NoDataFoundException {
        Query query = new Query();
        query.addCriteria(Criteria.where(QUEUE_NAME).is(queueName));

        Update update = new Update();
        update.inc(OFFSET);

        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
        findAndModifyOptions.upsert(true);
        findAndModifyOptions.returnNew(true);

        QueueMetaData metaData = mongoTemplate.findAndModify(query, update, findAndModifyOptions, QueueMetaData.class);
        if(Objects.nonNull(metaData)) {
            log.info("[LastOffset] queueName {} - metadata: {}", queueName, metaData);
            return metaData.getOffset();
        } else {
            throw new NoDataFoundException("No data found for offset of the associated queue");
        }
    }

    private Long notifyOneConsumer(QueueConsumer queueConsumer, QueueData queueData) throws Exception {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(queueConsumer.getCallBackURL())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        IConsumerCallback consumerCallback = retrofit.create(IConsumerCallback.class);

        List<QueueData> queueDataList = queueDataRepo.findByQueueNameAndOffsetGreaterThan(queueData.getQueueName(), queueConsumer.getOffset());
        log.info("[NotifyConsumer] List of data for queueConsumer {} - queueName {} - offset" +
                        " {} = {}", queueConsumer, queueData.getQueueName(),
                queueData.getOffset(), queueDataList);

        Response<Long> response = consumerCallback.notifyConsumer(queueDataList).execute();
        log.info("[NotifyConsumer] Response received for queueConsumer {} - queueName {} - offset" +
                " {} = {}", queueConsumer.getConsumerName(), queueData.getQueueName(),
                queueData.getOffset(), response);
        if (Objects.nonNull(response) &&
                response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(String.valueOf(response.errorBody()));
        }
    }
}
