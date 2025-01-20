package com.my.obuch.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaAdmin confKafAdmin() {
        Map<String, Object> conf = new HashMap<>();
        conf.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, List.of("localhost:29099", "localhost:39099", "localhost:49099"));

        return new KafkaAdmin(conf);
    }

    @Bean
    public NewTopic newTopic() {
        return new NewTopic("testik", 2, (short) 3);
    }

    @Bean
    public NewTopic newTopic2() {
        return new NewTopic("testik2", 2, (short) 3);
    }
}
