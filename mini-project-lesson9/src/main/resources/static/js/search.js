// Chờ toàn bộ HTML được load xong rồi mới chạy JavaScript
document.addEventListener("DOMContentLoaded", function () {

    // Lấy ô input tìm kiếm có id="searchKeyword"
    const searchInput = document.getElementById("searchKeyword");

    // Lấy button tìm kiếm có id="searchBtn"
    const searchBtn = document.getElementById("searchBtn");

    // Nếu không tìm thấy input hoặc button thì dừng chương trình
    if (!searchInput || !searchBtn) {
        return;
    }

    // Hàm xử lý logic tìm kiếm
    function performSearch() {

        // Lấy giá trị người dùng nhập và xóa khoảng trắng đầu/cuối
        const keyword = searchInput.value.trim();

        // Nếu từ khóa có từ 2 ký tự trở xuống thì không cho tìm kiếm
        if (keyword.length <= 2) {

            // Hiển thị thông báo cho người dùng
            alert("Từ khóa tìm kiếm phải lớn hơn 2 ký tự.");

            // Dừng hàm, không chạy các dòng phía dưới
            return;
        }

        // Chuyển trình duyệt đến trang tìm kiếm
        // encodeURIComponent() giúp mã hóa ký tự đặc biệt trong keyword. VD: iphone 15 pro -> iphone%2015%20pro
        window.location.href =
            "/product_search?keyword=" + encodeURIComponent(keyword);
    }

    // Khi người dùng click vào button Search
    // thì gọi hàm performSearch
    searchBtn.addEventListener("click", performSearch);

    // Theo dõi sự kiện nhấn phím trong ô input tìm kiếm
    searchInput.addEventListener("keydown", function (event) {

        // Kiểm tra phím vừa nhấn có phải Enter không
        if (event.key === "Enter") {

            // Ngăn hành vi mặc định của Enter
            // Ví dụ: tự submit form
            event.preventDefault();

            // Gọi hàm tìm kiếm
            performSearch();
        }
    });
});