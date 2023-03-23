package com.training.queue.constant;

public class ControllerConstant {

    public static final String BASE_CONTROLLER_PATH = "/queue/api/v1/";
    public static final String QUEUE_META_CONTROLLER_PATH = BASE_CONTROLLER_PATH + "queue_metadata";
    public static final String QUEUE_CONSUMER_CONTROLLER_PATH = BASE_CONTROLLER_PATH +
            "queue_consumers";
    public static final String DUMMY_CONSUMER_CONTROLLER_PATH = BASE_CONTROLLER_PATH +
            "debug";
    public static final String HEALTH_CONTROLLER_PATH = BASE_CONTROLLER_PATH + "health";
    public static final String QUEUE_OPERATION_CONTROLLER_PATH = BASE_CONTROLLER_PATH +
            "queue_operations";

}
