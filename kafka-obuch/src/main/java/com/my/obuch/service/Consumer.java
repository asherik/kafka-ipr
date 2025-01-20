package com.my.obuch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @KafkaListener(topics = "testik", groupId = "testik_group")
    public void listenGroupTestik(String message) {
        log.info("Сообщение: {}", message);
    }

}
