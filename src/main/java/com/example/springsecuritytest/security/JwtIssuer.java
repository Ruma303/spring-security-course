package com.example.springsecuritytest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties jwtProperties;

    private final Integer EXPIRATION_DAYS = 1; // 1 day expiration

    public String issue(Long userId, String email, List<String> roles) {
        return JWT
                .create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(this.getExpiration()) // 1 hour expiration
                .withClaim("email", email)
                .withClaim("roles", roles)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));  // Replace with your secret key
    }

    private Instant getExpiration() {
        return Instant.now().plus(Duration.of(EXPIRATION_DAYS, ChronoUnit.DAYS)); // Replace with your issuer
    }
}
