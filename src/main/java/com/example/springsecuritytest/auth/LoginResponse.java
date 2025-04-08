package com.example.springsecuritytest.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private String username;
    private String role;

    public static LoginResponse from(String token, String username, String role) {
        return LoginResponse.builder()
                .token(token)
                .username(username)
                .role(role)
                .build();
    }
}
