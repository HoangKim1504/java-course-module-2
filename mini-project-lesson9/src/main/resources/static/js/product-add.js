// Chờ HTML load xong hoàn toàn rồi mới chạy JavaScript
document.addEventListener("DOMContentLoaded", function () {

    // Lấy form thêm sản phẩm theo id="productAddForm"
    const form = document.getElementById("productAddForm");

    // Nếu trang hiện tại không có form này thì dừng script để tránh lỗi null
    if (!form) {
        return;
    }

    // Lắng nghe sự kiện submit của form async vì bên trong có dùng await để gọi API
    form.addEventListener("submit", async function (event) {

        // Ngăn hành vi submit mặc định của form
        // Nếu không có dòng này, browser có thể reload/chuyển trang ngay
        event.preventDefault();

        // Gọi hàm validateProductForm() từ product-validation.js
        // Nếu dữ liệu hợp lệ → trả về object product
        // Nếu không hợp lệ → trả về null
        const payload = validateProductForm();

        // Nếu validation thất bại, payload = null → dừng, không gọi API
        if (!payload) {
            return;
        }

        try {
            // Gọi API thêm sản phẩm bằng HTTP POST
            const response = await fetch("/api/products", {

                // Phương thức HTTP là POST
                method: "POST",

                // Báo cho backend biết dữ liệu gửi lên là JSON
                headers: {
                    "Content-Type": "application/json"
                },

                // Chuyển object JavaScript thành chuỗi JSON để gửi trong request body
                body: JSON.stringify(payload)
            });

            // Nếu backend trả HTTP 400 Bad Request thường là lỗi Bean Validation
            if (response.status === 400) {

                // Đọc JSON lỗi từ server rồi truyền cho applyServerErrors()
                // để hiển thị lỗi vào đúng field trên form
                applyServerErrors(await response.json());

                // Dừng xử lý
                return;
            }

            // response.ok = true khi status nằm trong khoảng 200-299
            // Nếu server trả 404, 500,... thì coi là lỗi
            if (!response.ok) {

                // Chủ động tạo exception để nhảy xuống catch
                throw new Error("Request failed");
            }

            // Nếu thêm thành công đọc JSON response từ backend
            // và convert thành object JavaScript
            const product = await response.json();

            // Hiển thị thông báo thêm sản phẩm thành công
            // product.id và product.title lấy từ response của backend
            alert("Thêm sản phẩm thành công! ID: " + product.id + " — " + product.title);

            // Sau khi thêm thành công
            // chuyển sang trang chi tiết của sản phẩm vừa tạo
            window.location.href = "/product_detail?id=" + product.id;

        } catch (error) {
            // Chạy khi:
            // - lỗi network
            // - server lỗi
            // - response không phải 2xx và bị throw Error ở trên
            alert("Thêm sản phẩm thất bại. Vui lòng thử lại.");
        }
    });
});