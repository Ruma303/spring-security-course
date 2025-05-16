package com.example.springsecurity.controller;

import com.example.springsecurity.dto.RegisterUser;
import com.example.springsecurity.dto.RegisterUserResponse;
import com.example.springsecurity.security.jwt.JwtUtils;
import com.example.springsecurity.security.jwt.LoginRequest;
import com.example.springsecurity.security.jwt.LoginResponse;
import com.example.springsecurity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUser registerUser) {
        userService.createUser(registerUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterUserResponse("Utente " + registerUser.username() + " registrato con successo!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(userDetails);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            LoginResponse loginResponse = new LoginResponse(jwt, userDetails.getUsername(), roles);
            return ResponseEntity.ok(loginResponse);

        } catch (Exception e) {
            Map<String, Object> badResponse = Map.of(
                    "status", HttpStatus.UNAUTHORIZED,
                    "message", "Autenticazione fallita!",
                    "error", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(badResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout riuscito!");
    }
}