package com.demo.external.service;

import com.demo.external.config.DummyJsonProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final RestClient restClient;
    private final DummyJsonProperties dummyJsonProperties;

    /**
     * Đoạn code này dùng để gọi API bên ngoài dummyjson.com để lấy danh sách products
     *
     * Luồng tổng thể:
     * fetchProducts(10)
     *        ↓
     * getBaseUrl()
     *        ↓
     * https://dummyjson.com
     *        ↓
     * tạo URL:
     * /products?limit={limit}
     *        ↓
     * .uri(url, 10)
     *        ↓
     * https://dummyjson.com/products?limit=10
     *        ↓
     * RestClient GET
     *        ↓
     * DummyJSON API
     *        ↓
     * JSON response
     *        ↓
     * JsonNode
     *        ↓
     * return
     */
    public JsonNode fetchProducts(int limit) {
        // Tạo URL ban đầu: https://dummyjson.com/products?limit={limit}
        String url = dummyJsonProperties.getBaseUrl() + "/products?limit={limit}";
        log.debug("Fetching products: limit={}", limit);
        return restClient
                .get() // Cho biết HTTP method là: GET
                .uri(url, limit) // Spring sẽ thay: {limit} bằng giá trị của biến limit
                .retrieve() // Thực hiện request và bắt đầu lấy response từ API
                .body(JsonNode.class); // Chuyển JSON response thành JsonNode
    }

    public JsonNode fetchCategories() {
        String url = dummyJsonProperties.getBaseUrl() + "/products/categories";
        return restClient
                .get()
                .uri(url)
                .retrieve()
                .body(JsonNode.class);
    }

    public JsonNode fetchUsers(int limit) {
        String url = dummyJsonProperties.getBaseUrl() + "/users?limit={limit}";
        return restClient
                .get()
                .uri(url, limit)
                .retrieve()
                .body(JsonNode.class);
    }

    public JsonNode fetchUserById(long id) {
        String url = dummyJsonProperties.getBaseUrl() + "/users/{id}";
        return restClient
                .get()
                .uri(url, id)
                .retrieve()
                .body(JsonNode.class);
    }
}
