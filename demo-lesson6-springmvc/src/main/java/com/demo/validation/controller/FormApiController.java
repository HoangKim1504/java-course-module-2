package com.demo.validation.controller;

import com.demo.servicelayer.model.Account;
import com.demo.servicelayer.service.AccountService;
import com.demo.validation.model.ValidationAccount;
import com.demo.validation.model.ValidationErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class FormApiController {

    private final AccountService accountService;
    private final AtomicLong idSequence = new AtomicLong(1);

    @PostMapping("/fill")
    public ResponseEntity<?> fillTheForm(
            @Valid @RequestBody ValidationAccount validationAccount,
            BindingResult bindingResult
    ) {
        // Cách 1: chỉ hiện nội dung lỗi
//        if (bindingResult.hasErrors()) {
//            List<String> errors = new ArrayList<>();
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                errors.add(error.getDefaultMessage());
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//        }

        // Cách 2: hiện tên item và nội dung lỗi
        if (bindingResult.hasErrors()) {
            List<ValidationErrorResponse> errors = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(new ValidationErrorResponse(
                        error.getField(),
                        error.getDefaultMessage()
                ));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        //  Mapping DTO → Model
        Account account = new Account();
        account.setId(idSequence.getAndIncrement());
        account.setUserName(validationAccount.getUsername());
        account.setEmail(validationAccount.getEmail());
        account.setPassword(validationAccount.getPassword());
        account.setAge(validationAccount.getAge());

        // Gọi AccountService xử lý đăng ký
        accountService.save(account);

        return ResponseEntity.ok().build();

    }
}
