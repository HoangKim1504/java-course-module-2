package com.demo.put.controller;

import com.demo.put.dto.GameRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/games")
public class GameCreateController {

    @PostMapping("/json")
    public ResponseEntity<GameRequest> createCategoryFromForm(
            @RequestBody GameRequest request
    ) {
        System.out.println("Body data: " + request.getName() + ", " + request.getPrice() + ", " + request.getPlatform());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
