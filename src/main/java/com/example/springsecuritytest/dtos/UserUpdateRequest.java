package com.example.springsecuritytest.dtos;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String password;
    private String email;
}
