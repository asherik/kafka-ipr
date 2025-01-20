package com.my.obuch.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @KafkaListener(topics = "testik", groupId = "testik_group")
    public void listenGroupTestik(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {

            log.info("Получено сообщение: {}", record.value());

            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Ошибка обработки сообщения: {}", record.value(), e);

        }
    }
}
