package com.example.springsecuritytest.services;

import com.example.springsecuritytest.entities.User;
import com.example.springsecuritytest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}