package com.proj.stockmonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaIprApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaIprApplication.class, args);
    }

}
