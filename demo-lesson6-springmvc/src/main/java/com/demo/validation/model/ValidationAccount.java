package com.demo.validation.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ValidationAccount {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "You must be at least 18 years old")
    private Integer age;
}
