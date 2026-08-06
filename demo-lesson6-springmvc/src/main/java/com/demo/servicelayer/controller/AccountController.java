package com.demo.servicelayer.controller;

import com.demo.servicelayer.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signUp")
    public ResponseEntity<Boolean> signUpAccount(@RequestParam String emailAddress) {
        return ResponseEntity.ok(accountService.validEmailFormat(emailAddress));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<String>> getOrder(@RequestParam String userId) {
        List<String> orders = accountService.getOrders(userId);
        return ResponseEntity.ok(orders);
    }
}
