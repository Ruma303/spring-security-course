package com.example.springsecurity.config;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.Role.RoleName;
import com.example.springsecurity.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleSeederConfig {

    @Bean
    public CommandLineRunner seedRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                for (RoleName roleName : RoleName.values()) {
                    Role role = new Role();
                    role.setName(roleName);
                    roleRepository.save(role);
                }
            }
        };
    }
}
