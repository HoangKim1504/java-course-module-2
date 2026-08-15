package com.demo.controller.web;

import com.demo.dto.ProductListResponse;
import com.demo.exception.ProductNotFoundException;
import com.demo.model.Product;
import com.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller cho các trang web HTML (render bằng Thymeleaf) của website bán hàng.
 *
 * <p>Mỗi phương thức xử lý một request {@code GET}, gọi {@link ProductService} để lấy dữ liệu,
 * đẩy vào {@link Model} rồi trả về tên template (vd. {@code "home"} → {@code templates/home.html}).
 * Riêng thao tác thêm/sửa sản phẩm chỉ trả về form, việc gửi dữ liệu do JavaScript gọi qua
 * {@code ProductApiController}.</p>
 */
@Controller
@RequiredArgsConstructor
public class ProductWebController {

    /**
     * Kích thước trang mặc định khi không truyền tham số {@code size}.
     */
    private static final int DEFAULT_PAGE_SIZE = 12;

    private final ProductService productService;

    /**
     * Điều hướng trang gốc {@code "/"} về trang chủ {@code /home}.
     *
     * @return chỉ thị redirect tới {@code /home}
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    /**
     * Hiển thị trang chủ với danh sách sản phẩm — hỗ trợ lọc theo nhóm, phân trang và sắp xếp.
     *
     * <p>Các tham số đều tùy chọn: {@code category} để lọc nhóm, {@code page}/{@code size} để
     * phân trang, {@code sortBy}/{@code order} để sắp xếp. Sau khi lấy dữ liệu, hàm tính tổng
     * số trang rồi đẩy đầy đủ thông tin sang view để vẽ thanh phân trang và bộ sắp xếp.</p>
     *
     * @return template {@code home}
     */
    @GetMapping("/home")
    public String home(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "12") int size,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "order", defaultValue = "asc") String order,
            Model model) {
        ProductListResponse response = productService.getProducts(category, page, size, sortBy, order);
        addPagingAttributes(model, response, page, size, sortBy, order);
        model.addAttribute("category", category);
        return "home";
    }

    /**
     * Hiển thị trang danh sách các nhóm (category) sản phẩm.
     *
     * @return template {@code category}
     */
    @GetMapping("/category")
    public String categories(Model model) {
        model.addAttribute("categories", productService.getCategories());
        return "category";
    }

    /**
     * Hiển thị trang chi tiết của một sản phẩm theo id truyền qua {@code ?id=}.
     *
     * @return template {@code product-detail}
     */
    @GetMapping("/product_detail")
    public String productDetail(@RequestParam("id") long id, Model model) {
        model.addAttribute("product", requireProduct(id));
        return "product-detail";
    }

    /**
     * Tính toán và đẩy các thuộc tính phân trang/sắp xếp sang view (dùng chung cho home và search).
     */
    private void addPagingAttributes(Model model, ProductListResponse response, int page, int size, String sortBy, String order) {
        int total = response.getTotal() == null ? 0 : response.getTotal();
        int effectiveSize = size <= 0 ? DEFAULT_PAGE_SIZE : size;
        int totalPages = total == 0 ? 1 : (int) Math.ceil((double) total / effectiveSize);
        int currentPage = Math.min(Math.max(page, 1), totalPages);

        model.addAttribute("products", response.getProducts());
        model.addAttribute("total", total);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", effectiveSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
    }

    /**
     * Lấy sản phẩm theo id, ném {@link ProductNotFoundException} nếu không tồn tại
     * để bộ xử lý lỗi hiển thị trang 404.
     */
    private Product requireProduct(long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }
}
