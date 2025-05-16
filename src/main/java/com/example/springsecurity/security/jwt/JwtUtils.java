package com.example.springsecurity.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.application.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.application.jwtExpirationMs}")
    private int jwtExpirationMs;

    private Key key() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        logger.debug("Header auth: {}", headerAuth);
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    public String getUsernameFromJwtToken(String token) {
        return getAllClaimsFromJwtToken(token).getSubject();
    }

    public Claims getAllClaimsFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build().parseSignedClaims(token);
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("Jwt claims string is empty: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid Jwt Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Jwt Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Jwt Token is unsupported: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("Jwt signature is invalid: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("JwtToken is invalid: {}", e.getMessage());
        }
        return false;
    }
}