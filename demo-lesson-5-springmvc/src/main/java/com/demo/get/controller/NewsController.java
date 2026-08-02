package com.demo.get.controller;

import com.demo.get.dto.NewsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    @GetMapping(
            value = "/latest",
            produces = MediaType.APPLICATION_JSON_VALUE // Dùng produces khi muốn chỉ định rõ API trả về loại dữ liệu nào
    )
    public NewsDto getLatestNews() {
        return new NewsDto("Michael", 45);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id) {
        if (id == 1L) {
            NewsDto news = new NewsDto("Michael", 45);
            return new ResponseEntity<>(news, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

}
