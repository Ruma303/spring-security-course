package com.example.springsecuritytest.controllers;

import com.example.springsecuritytest.auth.LoginRequest;
import com.example.springsecuritytest.auth.LoginResponse;
import com.example.springsecuritytest.auth.RegisterRequest;
import com.example.springsecuritytest.entities.User;
import com.example.springsecuritytest.security.JwtIssuer;
import com.example.springsecuritytest.security.UserPrincipal;
import com.example.springsecuritytest.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            var userPrincipal = (UserPrincipal) authentication.getPrincipal();

            var roles = userPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            var token = jwtIssuer.issue(userPrincipal.getId(), userPrincipal.getUsername(), roles);

            return LoginResponse.from(token, userPrincipal.getUsername(), roles.toString());

        } catch (Exception ex) {
            ex.printStackTrace(); // stampa lo stacktrace per capire il motivo
            throw new RuntimeException("Login fallito: " + ex.getMessage(), ex);
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword()); // sarà codificata nel service
        user.setEmail(registerRequest.getEmail());
        user.setRole("USER"); // ruolo assegnato lato server di default

        userService.createUser(user); // usa il service che codifica la password

        return "Utente registrato con successo";
    }
}