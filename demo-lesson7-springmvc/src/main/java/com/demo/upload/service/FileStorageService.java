package com.demo.upload.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    private final Path uploadRoot;

    // "${app.upload.dir}" -> khi upload hình thì sẽ biết được đưa vào folder nào
    public FileStorageService(@Value("${app.upload.dir") String uploadDir) throws IOException {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadRoot); // giúp tạo đường dẫn nếu chưa có
        log.info("Upload root initialized: {}", uploadRoot);
    }

    /**
     * Lưu file vào subFolder (vd: "misc", "avatars") và trả URL public.
     */
    public String store(MultipartFile file, String subFolder) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Chỉ chấp nhận ảnh: JPEG, PNG, GIF, WEBP");
        }

        // Đổi tên file gốc để không lấy nhầm hình
        String originalName = Paths.get(file.getOriginalFilename()).getFileName().toString();
        String extension = "";
        int dot = originalName.lastIndexOf("."); // tìm dấu . cuối cùng
        if (dot > 0) {
            extension = originalName.substring(dot); // VD: extension = ".jpg"
        }

        // UUID.randomUUID() -> chuỗi được gen ra tự động
        String savedName = UUID.randomUUID() + extension; // VD: tên mới = chuỗi + ".jpg"
        Path targetDir = uploadRoot.resolve(subFolder);
        Files.createDirectories(targetDir); // giúp tạo đường dẫn nếu chưa có
        Path targetFile = targetDir.resolve(savedName);

        Files.copy(
                file.getInputStream(),                // dữ liệu file upload
                targetFile,                           // nơi lưu trên máy
                StandardCopyOption.REPLACE_EXISTING   // có rồi thì ghi đè
        );
        String publicUrl = "/uploads/" + subFolder + "/" + savedName;
        log.debug("Stored file: {} → {}", originalName, publicUrl);
        return publicUrl;
    }
}
