package com.proj.stockmonitoring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.proj.stockmonitoring.model.StockPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockPriceConsumer {

    @Value("${stock.price.topic}")
    private String stockPriceTopic;

    private final RedisTemplate<String, Double> redisTemplate;
    private final ObjectMapper objectMapper;
    private final WebSocketService webSocketService;

    public StockPriceConsumer(RedisTemplate<String, Double> redisTemplate, WebSocketService webSocketService) {
        this.redisTemplate = redisTemplate;
        this.webSocketService = webSocketService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @KafkaListener(topics = "${stock.price.topic}", groupId = "stock_group")
    public void listen(String message) {
        try {
            StockPrice stockPrice = objectMapper.readValue(message, StockPrice.class);
            redisTemplate.opsForValue().set("stock_price", stockPrice.getPrice());
            webSocketService.sendStockPrice(stockPrice.getPrice());
            log.info("Сохранено в редис и отправлено в веб сокет: " + stockPrice.getPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
