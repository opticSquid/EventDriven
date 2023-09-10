package com.sb.event.driven.deliveryboy.deliveryboy.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka-topic.name}")
    private String topicName;

    @Async("asyncExecutor")
    public CompletableFuture<Boolean> updateLocation(String location) {
        this.kafkaTemplate.send(topicName, location);
        return CompletableFuture.completedFuture(true);
    }
}
