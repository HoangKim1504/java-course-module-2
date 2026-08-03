package com.demo.put.controller;

import com.demo.put.dto.CategoryUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryUpdateController {

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategoryFromForm(
            @PathVariable String id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "active") String status
    ) {
        System.out.println("Update category: " + id);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Status: " + status);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/json")
    public ResponseEntity<CategoryUpdateRequest> updateCategoryFromBody(
            @PathVariable String id,
            @RequestBody CategoryUpdateRequest request
    ) {
        System.out.println("Update category " + id + ": " + request.getName());
        return ResponseEntity.ok(request);
    }
}
