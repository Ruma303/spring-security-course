package com.example.springsecuritytest.controllers;

import com.example.springsecuritytest.dtos.UserCreateRequest;
import com.example.springsecuritytest.dtos.UserResponse;
import com.example.springsecuritytest.dtos.UserUpdateRequest;
import com.example.springsecuritytest.entities.User;
import com.example.springsecuritytest.security.UserPrincipal;
import com.example.springsecuritytest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/test")
    public String testAuth(@AuthenticationPrincipal UserPrincipal user) {
        return "Utente autenticato: " + user.getUsername();
    }

    @Autowired
    private UserService userService;

    // Pubblica
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    // Pubblica
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.findUser(id);
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }

    // Protetta
    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRole("USER");

        User saved = userService.createUser(user);
        return UserResponse.fromEntity(saved);
    }

    // Protetta
    @PutMapping("/{id}")
    public UserResponse updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long id) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User saved =  userService.updateUser(user, id);
        return UserResponse.fromEntity(saved);
    }

    // Protetta
    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "Utente con id " + id + " eliminato con successo.";
    }
}
