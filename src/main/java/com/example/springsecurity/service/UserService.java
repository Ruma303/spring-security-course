package com.example.springsecurity.service;

import com.example.springsecurity.dto.RegisterUser;
import com.example.springsecurity.dto.UserRequest;
import com.example.springsecurity.dto.UserResponse;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(RegisterUser registerUser) {
        if (userRepository.existsByUsername(registerUser.username())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(registerUser.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(registerUser.username());
        user.setEmail(registerUser.email());
        user.setPassword(passwordEncoder.encode(registerUser.password()));

        Role userRole = roleRepository.findByName(Role.RoleName.USER)
                .orElseThrow(() -> new RuntimeException("Ruolo USER non presente"));
        user.getRoles().add(userRole);

        userRepository.save(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return new UserResponse(user.getUsername(), user.getEmail());
    }

    public UserResponse getCurrentUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return new UserResponse(user.getUsername(), user.getEmail());
    }

    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));

        userRepository.save(user);
        return new UserResponse(user.getUsername(), user.getEmail());
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        userRepository.delete(user);
    }
}