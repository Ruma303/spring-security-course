package com.example.springsecurity.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        // http.formLogin(withDefaults()); // Disattiviamo il login basato su form

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Disabilita la gestione della sessione
        );
        http.httpBasic(withDefaults());

        // Il logout viene disabilitato di default.
        // Possiamo riabilitarlo esplicitamente
//        http.logout(logout -> logout
//                .logoutUrl("/logout") // URL per il logout
//                .logoutSuccessHandler((request, response, authentication) -> {
//                    response.setStatus(HttpServletResponse.SC_OK); // Restituisce 200 OK al successo
//                })
//                .invalidateHttpSession(true) // Invalida la sessione HTTP
//                .clearAuthentication(true) // Cancella l'autenticazione
//        );
        return http.build();
    }
}
