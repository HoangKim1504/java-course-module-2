package com.demo.homework.service;

import com.demo.external.config.DummyJsonProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeworkProductService {

    private final RestClient restClient;                       // tái dùng bean phần external
    private final DummyJsonProperties dummyJsonProperties;

    public JsonNode fetchProductsByCategory(String category) {
        String url = dummyJsonProperties.getBaseUrl() + "/products/category/{category}";
        log.debug("Fetching products by category: {}", category);
        return restClient
                .get()
                .uri(url, category)
                .retrieve()
                .body(JsonNode.class);
    }
}