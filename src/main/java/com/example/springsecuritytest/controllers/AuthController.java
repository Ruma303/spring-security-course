package com.example.springsecuritytest.controllers;

import com.example.springsecuritytest.auth.LoginRequest;
import com.example.springsecuritytest.auth.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        return LoginResponse.builder()
                .token("token") // Solo come test
                .username(loginRequest.getUsername())
                .role("role") // Solo come test
                .build();
    }
}