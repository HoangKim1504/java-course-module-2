package com.demo.controller.api;

import com.demo.dto.ProductRequest;
import com.demo.model.Product;
import com.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API nội bộ đóng vai trò "Spring Proxy" cho thao tác ghi sản phẩm (thêm/sửa).
 *
 * <p>JavaScript trên các form add/edit gọi tới các endpoint {@code /api/products} ở đây,
 * controller chuyển tiếp xuống {@link ProductService} để gọi ra DummyJSON. Nhờ vậy trình duyệt
 * không gọi thẳng API ngoài, giúp tập trung xử lý ở backend.</p>
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    /**
     * Nhận yêu cầu thêm sản phẩm từ form (JSON body) và chuyển tiếp tới service.
     *
     * <p>{@code @Valid} kích hoạt kiểm tra dữ liệu phía server theo các ràng buộc khai báo
     * trong {@link ProductRequest}; nếu sai sẽ trả về HTTP 400 do bộ xử lý lỗi đảm nhiệm.</p>
     *
     * @return HTTP 200 kèm sản phẩm vừa tạo
     */
    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductRequest request) {
        Product product = productService.addProduct(request);
        return ResponseEntity.ok(product);
    }

}
