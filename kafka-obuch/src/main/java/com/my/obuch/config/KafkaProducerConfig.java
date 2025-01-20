package com.my.obuch.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, String> kafkaProducerFactory() {
        Map<String, Object> confProps = new HashMap<>();

        confProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, List.of("localhost:29099", "localhost:39099", "localhost:49099"));
        confProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        confProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        confProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "testik-transactional-id");

        DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(confProps);
        factory.setTransactionIdPrefix("testik-transactional-");
        return factory;
    }

    @Bean
    public KafkaTemplate<String, String> template() {
        return new KafkaTemplate<>(kafkaProducerFactory());
    }
}
