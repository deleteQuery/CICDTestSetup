package com.training.queue.controller;

import com.training.queue.constant.ControllerConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerConstant.HEALTH_CONTROLLER_PATH)
public class HealthController {

    @GetMapping()
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok(null);
    }
}
