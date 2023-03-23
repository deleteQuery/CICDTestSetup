package com.training.queue.controller;

import com.training.queue.exception.InvalidDataException;
import com.training.queue.exception.NoDataFoundException;
import com.training.queue.pojo.QueueConsumerRequest;
import com.training.queue.pojo.QueueMetaRequest;
import com.training.queue.pojo.QueueMetaResponse;
import com.training.queue.service.IQueueConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.training.queue.constant.ControllerConstant.QUEUE_CONSUMER_CONTROLLER_PATH;

@RestController
@RequestMapping(QUEUE_CONSUMER_CONTROLLER_PATH)
@Slf4j
public class QueueConsumerController {

    IQueueConsumerService consumerService;

    public QueueConsumerController(IQueueConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping()
    public ResponseEntity<Object> registerConsumer(@RequestBody QueueConsumerRequest queueMetaRequest) {
        try {
            return ResponseEntity.ok(consumerService.registerQueueConsumer(queueMetaRequest));
        } catch (InvalidDataException | NoDataFoundException exception) {
            return ResponseEntity.unprocessableEntity().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error - contact dev" +
                    " team");
        }
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteQueueConsumer(@RequestParam String queueConsumer) {
        try {
            consumerService.deleteQueueConsumer(queueConsumer);
            return ResponseEntity.ok(true);
        } catch (NoDataFoundException e) {
            return ResponseEntity.unprocessableEntity().body("No data found for queue consumer");
        } catch (Exception e) {
            log.error("Error while deleting the queueConsumer: {}", queueConsumer, e);
            return ResponseEntity.internalServerError().body("Contact dev team, some error in " +
                    "processing");
        }
    }

    @GetMapping()
    public ResponseEntity<Object> getQueueConsumer(@RequestParam String queueName) {
        try {
            return ResponseEntity.ok(consumerService.getQueueConsumer(queueName));
        } catch (NoDataFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error - contact dev" +
                    " team");
        }
    }
}
