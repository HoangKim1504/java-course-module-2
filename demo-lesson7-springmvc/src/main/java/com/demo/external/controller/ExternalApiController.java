package com.demo.external.controller;

import com.demo.external.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/external")
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    @GetMapping("/products")
    public ResponseEntity<JsonNode> getProducts(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(externalApiService.fetchProducts(limit));
    }

    @GetMapping("/categories")
    public ResponseEntity<JsonNode> getCategories() {
        return ResponseEntity.ok(externalApiService.fetchCategories());
    }

    @GetMapping("/users")
    public ResponseEntity<JsonNode> getUsers(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(externalApiService.fetchUsers(limit));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<JsonNode> getUserById(@PathVariable long id) {
        JsonNode user = externalApiService.fetchUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
