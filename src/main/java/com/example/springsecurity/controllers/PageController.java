package com.example.springsecurity.controllers;

import lombok.RequiredArgsConstructor;
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
         return "Hello, World!";
     }
}
