// Chờ toàn bộ HTML load xong rồi mới chạy JavaScript
document.addEventListener("DOMContentLoaded", function () {

    // Lấy button xóa sản phẩm theo id="deleteBtn"
    const deleteBtn = document.getElementById("deleteBtn");

    // Nếu trang hiện tại không có nút deleteBtn thì dừng script để tránh lỗi null
    if (!deleteBtn) {
        return;
    }

    // Lắng nghe sự kiện click vào nút Xóa async vì bên trong có dùng await để gọi API
    deleteBtn.addEventListener("click", async function () {

        // Lấy productId từ thuộc tính data-product-id của button
        // Ví dụ:
        // <button id="deleteBtn" data-product-id="5"> → productId = "5"
        const productId = deleteBtn.dataset.productId;

        // Hiển thị hộp thoại xác nhận trước khi xóa
        // confirm() trả về:
        // true  → user chọn OK
        // false → user chọn Cancel
        if (!confirm("Bạn có chắc muốn xóa sản phẩm này?")) {
            // Nếu user chọn Cancel thì dừng
            // không gửi request DELETE
            return;
        }

        try {

            // Gọi API xóa sản phẩm
            // Ví dụ productId = 5 → DELETE /api/products/5
            const response = await fetch(
                "/api/products/" + productId,
                {
                    // HTTP DELETE dùng để xóa resource
                    method: "DELETE"
                }
            );

            // response.ok = true nếu status nằm trong khoảng 200-299
            // Nếu backend trả 404, 500,... thì coi là lỗi
            if (!response.ok) {
                // Tạo exception để chuyển xuống catch
                throw new Error("Request failed");
            }

            // Nếu xóa thành công hiển thị thông báo cho user
            alert("Đã xóa sản phẩm ID: " + productId);

            // Sau khi xóa thành công, chuyển user về trang home
            window.location.href = "/home";

        } catch (error) {

            // Chạy khi:
            // - lỗi network
            // - server lỗi
            // - response không thành công và bị throw Error
            alert("Xóa sản phẩm thất bại. Vui lòng thử lại.");
        }
    });
});