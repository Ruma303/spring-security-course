package com.example.springsecurity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserResponse(
        @NotBlank @Size(min = 4, max = 50)
        String username,

        @NotBlank @Email @Size(max = 200)
        String email
) {}
