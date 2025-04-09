package com.example.springsecuritytest.repositories;

import com.example.springsecuritytest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
