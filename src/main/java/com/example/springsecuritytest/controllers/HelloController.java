package com.example.springsecuritytest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HelloController {

     @GetMapping("/hello")
     public String hello() {
         return "Hello, World!";
     }
}
