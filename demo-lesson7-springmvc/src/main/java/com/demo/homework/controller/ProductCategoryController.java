package com.demo.homework.controller;

import com.demo.homework.service.HomeworkProductService;
import tools.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/external")
public class ProductCategoryController {

    private final HomeworkProductService homeworkProductService;

    @GetMapping("/products/category/{name}")
    public ResponseEntity<JsonNode> getProductsByCategory(@PathVariable String name) {
        return ResponseEntity.ok(homeworkProductService.fetchProductsByCategory(name));
    }
}