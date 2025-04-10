package com.example.springsecuritytest.controllers;

import com.example.springsecuritytest.auth.LoginRequest;
import com.example.springsecuritytest.auth.LoginResponse;
import com.example.springsecuritytest.security.JwtIssuer;
import com.example.springsecuritytest.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var userPrincipal = (UserPrincipal) authentication.getPrincipal();

        var roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var token = jwtIssuer.issue(userPrincipal.getId(), userPrincipal.getUsername(), roles);

        return LoginResponse.from(token, userPrincipal.getUsername(), roles.toString());
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal principal) {
        return "Se vedi questo messaggio, sei loggato come " + principal.getUsername()
                + " e userID: " + principal.getId();
    }

    @GetMapping("/content")
    public String content() {
        return "Se vedi questo messaggio, sei autenticato!";
    }
}