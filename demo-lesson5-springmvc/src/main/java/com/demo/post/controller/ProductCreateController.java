package com.demo.post.controller;

import com.demo.post.dto.ProductCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductCreateController {

    @PostMapping
    public ResponseEntity<Void> createProductFromForm(
            @RequestParam String name,
            @RequestParam(required = false) String price,
            @RequestParam(defaultValue = "yellow") String color
    ) {
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Color: " + color);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/json")
    public ResponseEntity<ProductCreateRequest> createProductFromBody(
            @RequestBody ProductCreateRequest request
    ) {
        System.out.println("Body data: " + request.getName() + ", " + request.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

}