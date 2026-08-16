// Hàm đếm số từ trong một chuỗi text
function countWords(text) {

    // Xóa khoảng trắng ở đầu và cuối chuỗi
    const trimmed = text.trim();

    // Nếu sau khi trim mà chuỗi rỗng thì xem như có 0 từ
    if (!trimmed) {
        return 0;
    }

    // Tách chuỗi theo khoảng trắng /\s+/ nghĩa là 1 hoặc nhiều ký tự khoảng trắng
    // Sau đó lấy số phần tử của mảng để biết số từ
    return trimmed.split(/\s+/).length;
}

// Hàm lấy toàn bộ dữ liệu từ form thêm sản phẩm
function getProductFormData() {

    // Trả về một object chứa dữ liệu của các field
    return {

        // Lấy giá trị từ input id="title"
        // trim() để xóa khoảng trắng đầu và cuối
        title: document.getElementById("title").value.trim(),

        // Lấy mô tả sản phẩm
        description: document.getElementById("description").value.trim(),

        // Lấy giá sản phẩm
        // Hiện tại vẫn là String, chưa convert sang Number
        price: document.getElementById("price").value.trim(),

        // Lấy nhóm sản phẩm
        category: document.getElementById("category").value.trim(),

        // Lấy URL hình ảnh
        thumbnail: document.getElementById("thumbnail").value.trim(),

        // Lấy thương hiệu
        brand: document.getElementById("brand").value.trim()
    };
}

// Hàm hiển thị lỗi cho một field cụ thể
function setFieldError(fieldId, message) {

    // Tìm input theo id
    // Ví dụ fieldId = "title" → tìm id="title"
    const input = document.getElementById(fieldId);

    // Tìm vùng hiển thị lỗi
    // Ví dụ fieldId = "title" → tìm id="titleError"
    const error = document.getElementById(fieldId + "Error");

    // Thêm class Bootstrap "is-invalid" để input hiển thị trạng thái lỗi, thường là viền đỏ
    input.classList.add("is-invalid");

    // Gán nội dung lỗi vào vùng invalid-feedback
    error.textContent = message;
}

// Hàm xóa lỗi cũ của nhiều field
function clearFieldErrors(fieldIds) {

    // Duyệt qua từng fieldId trong mảng
    fieldIds.forEach(function (fieldId) {

        // Tìm input tương ứng
        const input = document.getElementById(fieldId);

        // Tìm vùng hiển thị lỗi tương ứng
        const error = document.getElementById(fieldId + "Error");

        // Xóa class lỗi khỏi input
        input.classList.remove("is-invalid");

        // Xóa nội dung thông báo lỗi cũ
        error.textContent = "";
    });
}

// Hàm chính dùng để validate form thêm sản phẩm
function validateProductForm() {

    // Danh sách các field cần validate
    const fields = [
        "title",
        "description",
        "price",
        "category",
        "thumbnail",
        "brand"
    ];

    // Trước mỗi lần validate, xóa toàn bộ lỗi cũ trên giao diện
    clearFieldErrors(fields);

    // Lấy dữ liệu hiện tại từ form
    const data = getProductFormData();

    // Ban đầu giả định form hợp lệ
    let isValid = true;

    // =========================
    // Validate title
    // =========================

    // Nếu title rỗng
    if (!data.title) {

        // Hiển thị lỗi ở field title
        setFieldError("title", "Tên sản phẩm không được rỗng.");

        // Đánh dấu form không hợp lệ
        isValid = false;
    }

    // =========================
    // Validate description
    // =========================

    // Nếu description rỗng
    if (!data.description) {
        setFieldError("description", "Mô tả không được rỗng.");
        isValid = false;

    // Nếu description không rỗng nhưng có nhiều hơn 255 từ
    } else if (countWords(data.description) > 255) {
        setFieldError("description", "Mô tả tối đa 255 từ.");
        isValid = false;
    }

    // =========================
    // Validate price
    // =========================

    // Chuyển price từ String sang Number
    const price = Number(data.price);

    // Nếu người dùng chưa nhập giá
    if (!data.price) {
        setFieldError( "price", "Giá không được rỗng.");
        isValid = false;

    // Nếu:
    // - price không phải số
    // - hoặc price < 1
    // - hoặc price > 200
    } else if (Number.isNaN(price) || price < 1 || price > 200) {
        setFieldError("price", "Giá phải từ 1 đến 200 USD.");
        isValid = false;
    }

    // =========================
    // Validate category
    // =========================

    // Nếu category rỗng
    if (!data.category) {
        setFieldError("category", "Nhóm sản phẩm không được rỗng.");
        isValid = false;
    }

    // =========================
    // Validate thumbnail
    // =========================

    // Nếu URL hình ảnh rỗng
    if (!data.thumbnail) {
        setFieldError("thumbnail", "URL hình ảnh không được rỗng.");
        isValid = false;
    }

    // =========================
    // Validate brand
    // =========================

    // Nếu brand rỗng
    if (!data.brand) {
        setFieldError("brand", "Thương hiệu không được rỗng.");
        isValid = false;
    }

    // Nếu có ít nhất một field bị lỗi
    if (!isValid) {
        // Trả null để báo cho code gọi hàm biết:
        // form chưa hợp lệ, không nên tiếp tục gửi dữ liệu
        return null;
    }

    // Nếu tất cả field đều hợp lệ trả về object sản phẩm đã được validate
    return {
        title: data.title,
        description: data.description,
        // Dùng biến price đã convert sang Number
        price: price,
        category: data.category,
        thumbnail: data.thumbnail,
        brand: data.brand
    };
}

// Hàm dùng để hiển thị lỗi do Backend trả về
// Ví dụ Spring Bean Validation trả:
// {
//     "title": "Tên sản phẩm không được để trống",
//     "price": "Giá phải lớn hơn 0"
// }
function applyServerErrors(errors) {

    // Nếu server không trả lỗi thì không cần xử lý gì thêm
    if (!errors) {
        return;
    }

    // Object.keys(errors) lấy danh sách tên field bị lỗi
    // Ví dụ:
    // errors = {
    //     title: "...",
    //     price: "..."
    // }
    //
    // Object.keys(errors) → ["title", "price"]
    Object.keys(errors).forEach(function (fieldId) {

        // Chỉ xử lý nếu field đó thực sự tồn tại trong HTML
        if (document.getElementById(fieldId)) {

            // Dùng lại hàm setFieldError() để hiển thị lỗi server lên đúng field
            setFieldError(fieldId, errors[fieldId]);
        }
    });
}