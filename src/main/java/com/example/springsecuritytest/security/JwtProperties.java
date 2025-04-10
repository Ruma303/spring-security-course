package com.example.springsecuritytest.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {

    private String secretKey;
    private long expiration;

    private TokenProperties token = new TokenProperties();

    @Getter
    @Setter
    public static class TokenProperties {
        private String header;
        private String prefix;
    }
}