package com.example.springsecurity.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@AllArgsConstructor
//TODO Non implementata
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true; // oppure logica personalizzata
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // oppure logica personalizzata
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // oppure logica personalizzata
    }

    @Override
    public boolean isEnabled() {
        return true; // oppure logica personalizzata
    }
}