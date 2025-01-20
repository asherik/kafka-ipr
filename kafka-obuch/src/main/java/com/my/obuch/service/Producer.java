package com.my.obuch.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    @Transactional
    public void sendMessage() {
        int i = 8;
        for (int j = 0; j < i; j++) {
            kafkaTemplate.send("testik", "sadsadsa");
            Thread.sleep(2000);
            // throw new RuntimeException("портим");
        }
        Thread.sleep(10000);
    }
}
