package com.demo.delete.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderDeleteController {

    @DeleteMapping
    public ResponseEntity<Void> deleteOrderByQuery (
            @RequestParam String id
    ) {
        System.out.println("Delete order id: " + id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById (
            @PathVariable String id
    ) {
        System.out.println("Delete order id: " + id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteOrdersFromBody (
            @RequestBody Map<String, Object> body
    ) {
        System.out.println("Body data: " + body);
        return ResponseEntity.noContent().build();
    }

}
