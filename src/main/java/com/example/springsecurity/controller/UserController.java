package com.example.springsecurity.controller;

import com.example.springsecurity.dto.UserRequest;
import com.example.springsecurity.dto.UserResponse;
import com.example.springsecurity.security.CustomUserDetails;
import com.example.springsecurity.security.UserDetailsImpl;
import com.example.springsecurity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    //? Ottenere informazioni da JWT
    @GetMapping("/info-from-jwt")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getInfoFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // normalmente lo username
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String info = "Username: " + username + ", Ruoli: " + roles;
        return ResponseEntity.ok(info);
    }

    @GetMapping("/info")
    public ResponseEntity<String> info(Authentication authentication) {
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok("Username: " + username + ", Ruoli: " + roles);
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsernameByJwt(Principal principal) {
        return ResponseEntity.ok("Username: " + principal.getName());
    }

//    @GetMapping("/custom-info")
//    public ResponseEntity<String> getInfo(Authentication authentication) {
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        Long userId = userDetails.getId();
//        String email = userDetails.getEmail();
//        return ResponseEntity.ok("ID: " + userId + ", Email: " + email);
//    }


    //? CRUD con JWT
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        UserResponse userResponse = userService.getCurrentUser(userId);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        UserResponse userResponse = userService.updateUser(userId, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteUser(@Valid @RequestBody UserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}