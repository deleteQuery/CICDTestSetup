package com.training.queue.controller;

import com.training.queue.exception.InvalidDataException;
import com.training.queue.exception.NoDataFoundException;
import com.training.queue.pojo.QueueMetaRequest;
import com.training.queue.service.IQueueMetaService;
import com.training.queue.constant.ControllerConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(ControllerConstant.QUEUE_META_CONTROLLER_PATH)
public class QueueMetaController {

    IQueueMetaService queueMetaService;

    public QueueMetaController(IQueueMetaService queueMetaService) {
        this.queueMetaService = queueMetaService;
    }

    @PostMapping()
    public ResponseEntity<Object> createQueue(@RequestBody QueueMetaRequest queueMetaRequest) {
        try {
            return ResponseEntity.ok(queueMetaService.createQueue(queueMetaRequest));
        } catch (InvalidDataException exception) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error - contact dev" +
                    " team");
        }
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteQueue(@RequestParam String queueName) {
        try {
            queueMetaService.deleteQueue(queueName);
            return ResponseEntity.ok(true);
        } catch (NoDataFoundException e) {
            return ResponseEntity.unprocessableEntity().body("No data found for queue consumer");
        } catch (Exception e) {
            log.error("Error while deleting the queueConsumer: {}", queueName, e);
            return ResponseEntity.internalServerError().body("Contact dev team, some error in " +
                    "processing");
        }
    }

    @GetMapping()
    public ResponseEntity<Object> getQueue(@RequestParam String queueName) {
        try {
            return ResponseEntity.ok(queueMetaService.getQueue(queueName));
        } catch (NoDataFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error - contact dev" +
                    " team");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllPayloadsForAQueue(@RequestParam String queueName) {
        return ResponseEntity.ok(queueMetaService.getAllPayloadsOfAQueue(queueName));
    }
}
