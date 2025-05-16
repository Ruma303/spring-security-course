package com.example.springsecurity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, unique = true, nullable = false)
    private RoleName name;

    public enum RoleName {
        USER, ADMIN, MODERATOR, GUEST, EDITOR, CONTRIBUTOR, AUTHOR
    }
}