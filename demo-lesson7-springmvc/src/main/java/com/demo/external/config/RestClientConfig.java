package com.demo.external.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Đoạn code này dùng để tạo một RestClient và đăng ký nó vào Spring Container,
 * để các class khác có thể inject và dùng để gọi API bên ngoài.
 *
 * RestClientConfig:
 * - @Configuration: class chứa cấu hình Spring.
 * - @Bean: đăng ký object vào Spring Container.
 * - RestClient.Builder: builder dùng để tạo `RestClient`.
 * - builder.build(): tạo object `RestClient`.
 * - Sau đó có thể inject `RestClient` vào Service.
 * - RestClient: thường dùng để gọi REST API bên ngoài.
 * - Luồng: RestClient.Builder → build() → RestClient → Spring Container → Service
 *
 * Tổng thể luồng:
 * Application khởi động
 *         ↓
 * Spring đọc @Configuration
 *         ↓
 * Spring gọi @Bean restClient(...)
 *         ↓
 * RestClient.Builder
 *         ↓
 * builder.build()
 *         ↓
 * RestClient object
 *         ↓
 * đưa vào Spring Container
 *         ↓
 * Service có thể inject RestClient
 *         ↓
 * dùng RestClient gọi API khác
 */

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}
