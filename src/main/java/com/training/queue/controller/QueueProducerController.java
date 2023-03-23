package com.training.queue.controller;

import com.training.queue.constant.ControllerConstant;
import com.training.queue.exception.NoDataFoundException;
import com.training.queue.pojo.QueueOperationRequest;
import com.training.queue.service.IQueueProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstant.QUEUE_OPERATION_CONTROLLER_PATH)
public class QueueProducerController {

    IQueueProducerService producerService;

    public QueueProducerController(IQueueProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping()
    public ResponseEntity<Object> pushPayload(@RequestBody QueueOperationRequest operationRequest) {
        try {
            return ResponseEntity.ok(producerService.pushDataToQueue(operationRequest));
        } catch (NoDataFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error - contact dev" +
                    " team");
        }
    }
}
