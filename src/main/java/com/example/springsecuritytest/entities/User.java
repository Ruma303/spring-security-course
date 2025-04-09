package com.example.springsecuritytest.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String username;

    @Column(nullable = false, length = 500)
    private String password;

    @Column(length = 200)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String role;
}