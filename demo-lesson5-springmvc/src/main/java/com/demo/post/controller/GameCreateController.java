package com.demo.post.controller;

import com.demo.post.dto.GameCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/games")
public class GameCreateController {

    @PostMapping("/json")
    public ResponseEntity<GameCreateRequest> createCategoryFromForm(
            @RequestBody GameCreateRequest request
    ) {
        System.out.println("Body data: " + request.getName() + ", " + request.getPrice() + ", " + request.getPlatform());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
