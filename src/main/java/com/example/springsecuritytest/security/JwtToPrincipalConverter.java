package com.example.springsecuritytest.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {

    public UserPrincipal convert(DecodedJWT token) {
        return UserPrincipal.builder()
                .id(token.getClaim("id").asLong()) // claim personalizzato
                .username(token.getSubject()) // subject = username
                .authorities(extractAuthoritiesFromClaim(token))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("roles");
        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(String.class).stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}