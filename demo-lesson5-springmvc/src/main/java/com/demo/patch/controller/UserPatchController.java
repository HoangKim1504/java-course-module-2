package com.demo.patch.controller;

import com.demo.patch.dto.UserPatchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserPatchController {

    @PatchMapping("/{id}")
    public ResponseEntity<UserPatchRequest> patchUser (
            @PathVariable Long id,
            @RequestBody UserPatchRequest request
    ) {
        System.out.println("Patch product " + id);
        if (request.getAddress() != null) {
            System.out.println("  address: " + request.getAddress());
        }
        if (request.getPhone() != null) {
            System.out.println("  phone: " + request.getPhone());
        }
        return ResponseEntity.ok(request);
    }
}
