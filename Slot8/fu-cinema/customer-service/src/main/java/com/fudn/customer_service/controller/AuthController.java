package com.fudn.customer_service.controller;

import com.fudn.customer_service.dto.*;
import com.fudn.customer_service.service.AuthService;
import com.fudn.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CustomerService customerService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse register(@Valid @RequestBody RegisterRequest request) {
        return customerService.register(request);
    }
}
