package com.demo.validation.controller;

import com.demo.servicelayer.model.Account;
import com.demo.servicelayer.service.AccountService;
import com.demo.validation.model.ValidationAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final AccountService accountService;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("account", new ValidationAccount()); // object rỗng cho form
        return "validation/form";
    }

    @PostMapping
    public String submitform(
            @Valid @ModelAttribute("account") ValidationAccount validationAccount,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "validation/form"; // quay lại form — Thymeleaf hiển thị lỗi
        }

        //  Mapping DTO → Model
        Account account = new Account();
        account.setUserName(validationAccount.getUsername());
        account.setEmail(validationAccount.getEmail());
        account.setPassword(validationAccount.getPassword());
        account.setAge(validationAccount.getAge());

        // Gọi AccountService xử lý đăng ký
        accountService.save(account);

        return "redirect:/register/success"; // Post-Redirect-Get pattern
    }

    @GetMapping("/success")
    public String success() {
        return "validation/success";
    }
}
