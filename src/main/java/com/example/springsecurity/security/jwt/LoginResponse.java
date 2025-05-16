package com.example.springsecurity.security.jwt;

import java.util.List;

public record LoginResponse (String jwtToken, String username, List<String> roles) {}