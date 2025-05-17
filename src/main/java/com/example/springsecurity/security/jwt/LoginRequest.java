package com.example.springsecurity.security.jwt;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
        @NotBlank String username,
        @NotBlank String password
) {}
