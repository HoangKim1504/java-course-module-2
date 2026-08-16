// Chờ toàn bộ HTML load xong rồi mới chạy JavaScript
document.addEventListener("DOMContentLoaded", function () {

    // Lấy form chỉnh sửa sản phẩm theo id="productEditForm"
    const form = document.getElementById("productEditForm");

    // Nếu trang hiện tại không có form này thì dừng script
    if (!form) {
        return;
    }

    // Lấy productId từ thuộc tính data-product-id của form
    // Ví dụ:
    // <form id="productEditForm" data-product-id="5"> → productId = "5"
    const productId = form.dataset.productId;

    // Lắng nghe sự kiện submit của form chỉnh sửa sản phẩm
    // async vì bên trong có dùng await để gọi API
    form.addEventListener("submit", async function (event) {

        // Ngăn hành vi submit mặc định của form
        // để browser không reload/chuyển trang ngay lập tức
        event.preventDefault();

        // Gọi hàm validateProductForm() trong product-validation.js
        // Nếu hợp lệ → trả về object sản phẩm
        // Nếu không hợp lệ → trả về null
        const payload = validateProductForm();

        // Nếu validation thất bại thì dừng
        // và không gửi request lên server
        if (!payload) {
            return;
        }

        try {
            // Gọi API cập nhật sản phẩm
            // Ví dụ productId = 5 → PUT /api/products/5
            const response = await fetch("/api/products/" + productId, {

                // PUT dùng để cập nhật dữ liệu đã tồn tại
                method: "PUT",

                // Báo cho backend biết request body là JSON
                headers: {
                    "Content-Type": "application/json"
                },

                // Chuyển object JavaScript thành JSON string
                // để gửi trong request body
                body: JSON.stringify(payload)
            });

            // Nếu backend trả về 400 Bad Request
            // thường là lỗi Bean Validation
            if (response.status === 400) {

                // Đọc JSON lỗi từ backend
                // rồi hiển thị lỗi lên đúng field
                applyServerErrors(await response.json());

                // Dừng xử lý
                return;
            }

            // response.ok = true nếu HTTP status nằm trong khoảng 200-299
            // Nếu là 404, 500,... thì coi là lỗi
            if (!response.ok) {

                // Tạo exception để nhảy xuống catch
                throw new Error("Request failed");
            }

            // Nếu cập nhật thành công
            // đọc JSON response từ backend
            // và chuyển thành object JavaScript
            const product = await response.json();

            // Hiển thị thông báo cập nhật thành công
            alert(
                "Cập nhật sản phẩm thành công! ID: "
                + product.id
                + " — "
                + product.title
            );

            // Chuyển sang trang chi tiết của sản phẩm vừa cập nhật
            window.location.href = "/product_detail?id=" + product.id;

        } catch (error) {

            // Chạy khi:
            // - lỗi network
            // - server lỗi
            // - response không thành công và bị throw Error
            alert("Cập nhật sản phẩm thất bại. Vui lòng thử lại.");
        }
    });
});