package com.demo.get.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserQueryController {

    @GetMapping
    public ResponseEntity<List<String>> getAllUsers() {
        List<String> userNames = new ArrayList<>();
        userNames.add("Sarah");
        userNames.add("Mike");
        userNames.add("Kim Jong");
        return new ResponseEntity<>(userNames, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable String id) {
        System.out.println("Id value: " + id);
        return new ResponseEntity<>("User id: " + id, HttpStatus.OK);
    }

}