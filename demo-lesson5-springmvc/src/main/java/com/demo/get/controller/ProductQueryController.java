package com.demo.get.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductQueryController {

    @GetMapping
    public ResponseEntity<List<String>> getAllProducts() {
        List<String> productNames = new ArrayList<>();
        productNames.add("Samsung");
        productNames.add("iPhone");
        return new ResponseEntity<>(productNames, HttpStatus.OK);
    }

    @GetMapping("/search")
    public  ResponseEntity<String> searchProducts(
            @RequestParam String category,
            @RequestParam(required = false) String brand,
            @RequestParam(defaultValue = "name") String sortBy) {
        System.out.println("Category: " + category);
        System.out.println("Brand: " + brand);
        System.out.println("Sort by: " + sortBy);

        String message = "Category: " + category + ", sortBy: " + sortBy;
        if (brand != null && !brand.isBlank()) {
            message += ", brand: " + brand;
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable String id) {
        System.out.println("Id value: " + id);
        return new ResponseEntity<>("Product id: " + id, HttpStatus.OK);
    }

}