package com.example.springsecuritytest.controllers;

import com.example.springsecuritytest.auth.LoginRequest;
import com.example.springsecuritytest.auth.LoginResponse;
import com.example.springsecuritytest.security.JwtIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtIssuer jwtIssuer;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        var token = jwtIssuer.issue(1L, loginRequest.getUsername(), List.of("USER"));
        return LoginResponse.builder()
                .token(token)
                .build();
    }
}