package com.example.springsecurity.dto;

public record UserRequest(
        String username,
        String password,
        String email
) {}
