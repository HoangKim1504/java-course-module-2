package com.demo.patch.controller;

import com.demo.patch.dto.ProductPatchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductPatchController {

    @PatchMapping("/{id}")
    public ResponseEntity<ProductPatchRequest> patchProduct (
            @PathVariable Long id,
            @RequestBody ProductPatchRequest request
    ) {
        System.out.println("Patch product " + id);
        if (request.getName() != null) {
            System.out.println("  name: " + request.getName());
        }
        if (request.getPrice() != null) {
            System.out.println("  price: " + request.getPrice());
        }
        if (request.getColor() != null) {
            System.out.println("  color: " + request.getColor());
        }
        return ResponseEntity.ok(request);
    }

}
