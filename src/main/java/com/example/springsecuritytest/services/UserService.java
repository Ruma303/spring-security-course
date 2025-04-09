package com.example.springsecuritytest.services;

import com.example.springsecuritytest.entities.User;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    public Optional<User> findByEmail(String email) {
        if (!email.equalsIgnoreCase(email)) return Optional.empty();
        return Optional.of(User.builder()
                .id(1L)
                .username("admin")
                .email("admin@example.com")
                .password("password")
                .role("ROLE_ADMIN")
                .build());
    }
}
