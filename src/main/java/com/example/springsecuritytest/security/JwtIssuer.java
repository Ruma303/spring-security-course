package com.example.springsecuritytest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties jwtProperties;

    /**
     * Genera un token JWT con id utente, username e ruoli
     */
    public String issue(Long userId, String username, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("username", username)
                .withClaim("roles", roles)
                .withExpiresAt(getExpirationInstant())
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    /**
     * Calcola la scadenza del token usando il valore configurato in millisecondi
     */
    private Instant getExpirationInstant() {
        long expirationMillis = jwtProperties.getExpiration();
        return Instant.now().plusMillis(expirationMillis);
    }
}