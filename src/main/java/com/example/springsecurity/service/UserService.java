package com.example.springsecurity.service;

import com.example.springsecurity.dto.RegisterUser;
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
}