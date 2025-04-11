package com.example.springsecuritytest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class HelloController {

     @GetMapping("/hello")
     public String hello() {
         return "Hello, World!";
     }
}
