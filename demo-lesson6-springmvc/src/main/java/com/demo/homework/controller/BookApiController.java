package com.demo.homework.controller;

import java.util.ArrayList;
import java.util.List;

import com.demo.homework.model.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.demo.homework.dto.BookRequest;
import com.demo.homework.model.Book;
import com.demo.homework.service.BookService;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createBook(
            @Valid @RequestBody BookRequest request,
            BindingResult bindingResult) {
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
        Book created = bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}