package com.demo.external.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Đoạn code này có mục đích đọc cấu hình API DummyJSON từ application.properties vào một Java object,
 * để sau đó dùng trong Service/Client.
 *
 * DummyJsonProperties:
 * - @Data: Lombok tự tạo getter/setter.
 * - @Component: đăng ký class vào Spring Container.
 * - @ConfigurationProperties(prefix = "app.external.dummyjson"): đọc cấu hình từ `application.properties` và map vào class.
 * - base-url → map vào biến baseUrl.
 * - "https://dummyjson.com" là giá trị mặc định.
 *
 * Tổng thể luồng: ở file application.properties
 * app.external.dummyjson.base-url=https://dummyjson.com
 *                        ↓
 *         @ConfigurationProperties
 *                        ↓
 *           DummyJsonProperties
 *                        ↓
 *              baseUrl
 *                        ↓
 *         properties.getBaseUrl()
 */

@Data
@Component
@ConfigurationProperties(prefix = "app.external.dummyjson")
public class DummyJsonProperties {

    // giá trị mặc định khi file application.properties không có url
    private String baseUrl = "https://dummyjson.com";
}
