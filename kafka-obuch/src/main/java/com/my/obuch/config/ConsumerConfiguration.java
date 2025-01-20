package com.my.obuch.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ConsumerConfiguration {

    public ConsumerFactory<String, String> consumerFactory() {

        Map<String, Object> params = new HashMap<>();

        params.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, List.of("localhost:29099", "localhost:39099", "localhost:49099"));
        params.put(ConsumerConfig.GROUP_ID_CONFIG, "testik_group");
        params.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        params.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        params.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_uncommitted");

        return new DefaultKafkaConsumerFactory<>(params);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
