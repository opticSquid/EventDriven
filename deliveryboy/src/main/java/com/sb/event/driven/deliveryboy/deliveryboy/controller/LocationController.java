package com.sb.event.driven.deliveryboy.deliveryboy.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.event.driven.deliveryboy.deliveryboy.service.KafkaService;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/update")
    public ResponseEntity<?> updateLocation() {
        List<CompletableFuture<Boolean>> taskList = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            if (i > 50 && i % 50 == 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    break;
                }

            }
            taskList.add(this.kafkaService
                    .updateLocation(
                            "(" + Math.round(Math.random() * 100) + ", " + Math.round(Math.random() * 100) + ")"));
        }

        CompletableFuture.allOf(taskList.toArray(new CompletableFuture[0]));
        boolean hasErrorOccoured = taskList.parallelStream().anyMatch(task -> task.isCompletedExceptionally());
        if (hasErrorOccoured) {
            return new ResponseEntity<>(Map.of("message", "location not updated", "status", false),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(Map.of("message", "location updated", "status", true),
                    HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
