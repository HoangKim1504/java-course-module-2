package com.demo.put.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryCreateController {

    @PostMapping
    public ResponseEntity<Void> createCategoryFromForm(
            @RequestParam String name,
            @RequestParam(required = false) String location
    ) {
        System.out.println("Category: " + name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
