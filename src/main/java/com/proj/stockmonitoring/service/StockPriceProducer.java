package com.proj.stockmonitoring.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.proj.stockmonitoring.model.StockPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class StockPriceProducer {

    @Value("${stock.price.topic}")
    private String stockPriceTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public StockPriceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Scheduled(fixedRate = 1000)
    public void sendStockPrice() {
        try {
            String url = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities/GAZP.json?iss.only=marketdata";
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode marketData = root.path("marketdata").path("data");
            if (marketData.isArray() && !marketData.isEmpty()) {
                JsonNode firstEntry = marketData.get(0);
                double price = firstEntry.get(0).asDouble();
                StockPrice stockPrice = new StockPrice("GAZP", price, LocalDateTime.now());
                String stockPriceJson = objectMapper.writeValueAsString(stockPrice);
                kafkaTemplate.send(stockPriceTopic, stockPrice.getSymbol(), stockPriceJson);
                System.out.println("Sent to Kafka: " + stockPriceJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
