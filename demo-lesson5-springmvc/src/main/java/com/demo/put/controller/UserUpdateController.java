package com.demo.put.controller;

import com.demo.put.dto.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserUpdateController {

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserFromForm (
            @PathVariable String id,
            @RequestParam String name,
            @RequestParam(required = false) String address
    ) {
        System.out.println("Update User: " + id);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserUpdateRequest> updateUserFromBody (
            @PathVariable String id,
            @RequestBody UserUpdateRequest request
    ) {
        System.out.println("Update User " + id + ": " + request.getGender());
        return ResponseEntity.ok(request);
    }
}
