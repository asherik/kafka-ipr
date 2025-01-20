package com.my.obuch.sheduler;

import com.my.obuch.service.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

    private final Producer producer;

    @Scheduled(fixedRate = 200000000)
    public void reportCurrentTime() {
        producer.sendMessage();
    }
}
