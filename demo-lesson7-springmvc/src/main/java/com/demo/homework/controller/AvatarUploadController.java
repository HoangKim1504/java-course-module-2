package com.demo.homework.controller;

import com.demo.upload.dto.FileUploadResponse;
import com.demo.upload.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class AvatarUploadController {

    public final FileStorageService fileStorageService; // tái dùng service phần upload

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            // Lưu file vào folder "avatars"
            String url = fileStorageService.store(file, "avatars");
            return ResponseEntity.ok(new FileUploadResponse(url));
        } catch (IllegalArgumentException e) {
            log.warn("Avatar upload rejected: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Avatar upload failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Avatar upload failed: " + e.getMessage());
        }
    }
}
