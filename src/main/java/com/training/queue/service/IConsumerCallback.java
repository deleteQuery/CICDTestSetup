package com.training.queue.service;

import com.training.queue.model.QueueData;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;
import java.util.Map;

public interface IConsumerCallback {

    @POST(".")
    public Call<Long> notifyConsumer(@Body List<QueueData> payload);
}
