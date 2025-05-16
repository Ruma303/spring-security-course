//package com.example.springsecurity.security;
//
//import com.example.springsecurity.model.User;
//import com.example.springsecurity.repository.UserRepository;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import com.example.springsecurity.;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Email non trovata: " + email));
//
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getEmail()) // il valore restituito da getUsername() sarà l'email
//                .password(user.getPassword())
//                .roles(String.valueOf(user.getRoles())) // o authorities se serve granularità
//                .build();
//    }
//}
