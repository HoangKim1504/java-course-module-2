package com.demo.servicelayer.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    public List<String> getHistoricalOrder(String userId) {
        return List.of("pencil", "book", "ruler");
    }
}
