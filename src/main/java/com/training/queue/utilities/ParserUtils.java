package com.training.queue.utilities;

import com.training.queue.model.QueueConsumer;
import com.training.queue.model.QueueData;
import com.training.queue.model.QueueMetaData;
import com.training.queue.pojo.*;

import java.util.Date;

public class ParserUtils {

    public static final long ZERO_OFFSET = 0L;

    public static QueueMetaData getQueueMetaData(final QueueMetaRequest queueMetaRequest) {
        return QueueMetaData
                .builder()
                .queueName(queueMetaRequest.getQueueName())
                .createdBy(queueMetaRequest.getUserId())
                .updatedBy(queueMetaRequest.getUserId())
                .offset(ZERO_OFFSET)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public static QueueMetaResponse getQueueMetaResponse(final QueueMetaData queueMetaData) {
        return QueueMetaResponse
                .builder()
                .queueId(queueMetaData
                        .getId())
                .queueName(queueMetaData.getQueueName())
                .offset(queueMetaData.getOffset())
                .lastUpdatedBy(queueMetaData.getUpdatedBy())
                .lastUpdatedAt(queueMetaData.getUpdatedAt())
                .build();
    }


    public static QueueConsumer getQueueConsumer(final QueueConsumerRequest queueConsumerRequest,
                                                 final String queueId) {
        return QueueConsumer
                .builder()
                .consumerName(queueConsumerRequest.getConsumerName())
                .queueName(queueConsumerRequest.getQueueName())
                .callBackURL(queueConsumerRequest.getCallBackURL())
                .createdAt(new Date())
                .updatedAt(new Date())
                .queueId(queueId)
                .offset(ZERO_OFFSET)
                .build();
    }

    public static QueueOperationResponse getQueueOperationResponse(final QueueData queueData) {
        return QueueOperationResponse
                .builder()
                .queueName(queueData.getQueueName())
                .offset(queueData.getOffset())
                .isSuccessful(true)
                .build();
    }

    public static QueueData getQueueData(final QueueOperationRequest operationRequest,
                                             final String queueId, final Long currentOffset) {
        return QueueData
                .builder()
                .queueId(queueId)
                .queueName(operationRequest.getQueueName())
                .createdAt(new Date())
                .data(operationRequest.getPayload())
                .offset(currentOffset)
                .build();
    }

    public static QueueConsumerResponse getQueueConsumerResponse(final QueueConsumer queueConsumer) {
        return QueueConsumerResponse
                .builder()
                .consumerName(queueConsumer.getConsumerName())
                .offset(queueConsumer.getOffset())
                .queueId(queueConsumer.getQueueId())
                .queueName(queueConsumer.getQueueName())
                .offset(queueConsumer.getOffset())
                .lastUpdatedAt(queueConsumer.getUpdatedAt())
                .callBackURL(queueConsumer.getCallBackURL())
                .id(queueConsumer.getId())
                .build();
    }
}
