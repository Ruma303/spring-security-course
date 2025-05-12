package com.example.springsecurity.controllers;

import lombok.RequiredArgsConstructor;
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String user() {
        return "Benvenuto alla pagina user! Hai superato il login!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Benvenuto alla pagina admin! Hai superato il login!";
    }
}
