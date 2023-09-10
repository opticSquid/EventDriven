package com.sb.event.driven.enduser.enduser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConsumerConfig {
    // @Value("${kafka-consumer.topic-name}")
    // private static final String TOPIC = "";
    // @Value("${kafka-consumer.group-id:}")
    // private static final String GROUP_ID = "";

    @KafkaListener(topics = "location-update-topic", groupId = "group-1")
    public void updatedLocation(String value) {
        System.out.println(value);
    }
}