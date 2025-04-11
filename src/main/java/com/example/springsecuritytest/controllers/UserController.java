package com.example.springsecuritytest.controllers;

import com.example.springsecuritytest.dtos.UserResponse;
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
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Protetta
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        return userService.updateUser(user, id);
    }

    // Protetta
    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "Utente con id " + id + " eliminato con successo.";
    }
}
