package com.training.queue.controller;

import com.training.queue.model.QueueData;
import com.training.queue.pojo.QueueDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.training.queue.constant.ControllerConstant.DUMMY_CONSUMER_CONTROLLER_PATH;

@RestController
@RequestMapping(DUMMY_CONSUMER_CONTROLLER_PATH)
@Slf4j
public class DummyConsumerController {

    private Boolean isHappyScenario = true;

    @PostMapping()
    public ResponseEntity<Long> receiveCallback(@RequestBody List<QueueDataResponse> request) {
        log.info("[DEBUG CONSUMER] - {}", request);
        try {
            if(!isHappyScenario) {
                log.info("Returning non happy scenario - so that case of failure at consumer " +
                        "could be tested");
                return ResponseEntity.internalServerError().body(-1L);
            }
            List<Long> collect = request.stream().map(QueueDataResponse::getOffset).sorted().collect(Collectors.toList());
            log.info("[DEBUG CONSUMER] - {} and offsetList: {}", request, collect);
            return ResponseEntity.ok(collect.get(collect.size() - 1));
        } catch (Exception exception) {
            log.error("Error in debug consumer {}", exception.getMessage());
            return ResponseEntity.internalServerError().body(-1L);
        }
    }

    @PatchMapping()
    public void changeDummyDefaultConsumerBehaviour() {
        isHappyScenario = !isHappyScenario;
    }
}
