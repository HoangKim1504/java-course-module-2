package com.demo.delete.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/songs")
public class SongDeleteController {

    @DeleteMapping
    public ResponseEntity<Void> deleteSongByQuery (
            @RequestParam String title,
            @RequestParam(required = false) String theme
    ) {
        System.out.println("Delete song title: " + title);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongById (
            @PathVariable Long id
    ) {
         System.out.println("Delete song id: " + id);
         return ResponseEntity.noContent().build();
    }
}
