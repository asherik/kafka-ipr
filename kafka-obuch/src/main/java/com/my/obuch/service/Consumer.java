package com.my.obuch.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class Consumer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "testik", groupId = "testik_group")
    @Transactional
    public void listenGroupTestik(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {

            log.info("Получено сообщение: {}", record.value());


            kafkaTemplate.send("testik", "ответное сообщение");


            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Ошибка обработки сообщения: {}", record.value(), e);

        }
    }
}
