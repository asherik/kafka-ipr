package com.proj.stockmonitoring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockPrice {
    private String symbol;
    private double price;
    private LocalDateTime timestamp;
}
