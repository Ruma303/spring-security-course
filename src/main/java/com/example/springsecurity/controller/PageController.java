package com.example.springsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PageController {

    @GetMapping
    public String index() {
        return "Benvenuto alla pagina principale! Hai superato il login!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, user!";
    }

    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    @GetMapping("/user")
    public String allAuthenticated() {
        return "Hai superato il login!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Benvenuto alla pagina admin! Hai superato il login!";
    }

    @GetMapping("/secured")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getProtectedResource() {
        return ResponseEntity.ok("Benvenuto alla pagina principale! Hai superato il login!");
    }
}