package com.my.obuch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ObuchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObuchApplication.class, args);
    }

}
