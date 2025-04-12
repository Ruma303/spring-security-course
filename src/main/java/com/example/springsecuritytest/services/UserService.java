package com.example.springsecuritytest.services;

import com.example.springsecuritytest.entities.User;
import com.example.springsecuritytest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user, Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // NON modificare il ruolo
        // existing.setRole(...) → riga rimossa

        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
