package com.adriannebulao.tasktracker.config;

import com.adriannebulao.tasktracker.security.domain.Role;
import com.adriannebulao.tasktracker.security.persistence.RoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DataSeeder {

    private final RoleRepository roleRepository;

    private static final List<String> DEFAULT_ROLES = List.of("ADMIN", "USER");

    @PostConstruct
    @Transactional
    public void seedRoles() {
        for (String roleName : DEFAULT_ROLES) {
            roleRepository.findByName(roleName).orElseGet(() -> {
                Role role = Role.builder()
                        .name(roleName)
                        .build();
                return roleRepository.save(role);
            });
        }
    }
}
