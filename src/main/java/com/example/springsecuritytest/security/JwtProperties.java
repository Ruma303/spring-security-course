package com.example.springsecuritytest.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    /**
     * Chiave segreta per firmare/verificare il JWT
     */
    private String secretKey;

    /**
     * Durata del token in millisecondi (es: 3600000 = 1 ora)
     */
    private long expiration;

    /**
     * Configurazione dell'header HTTP da cui estrarre il token
     */
    private TokenProperties token = new TokenProperties();

    @Getter
    @Setter
    public static class TokenProperties {
        /**
         * Nome dell'header (es: Authorization)
         */
        private String header;

        /**
         * Prefisso del token (es: "Bearer ")
         */
        private String prefix;
    }
}