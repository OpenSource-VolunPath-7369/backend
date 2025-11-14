package com.acme.center.volunpath_backend.iam.infrastructure.persistence.jpa.repositories;

import com.acme.center.volunpath_backend.iam.domain.model.entities.Role;
import com.acme.center.volunpath_backend.iam.domain.model.valueobjects.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Data Initializer
 * Initializes roles in the database on application startup
 */
@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);
    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        initializeRoles();
    }

    private void initializeRoles() {
        LOGGER.info("Initializing roles...");
        
        for (Roles roleEnum : Roles.values()) {
            if (!roleRepository.existsByName(roleEnum)) {
                Role role = new Role(roleEnum);
                roleRepository.save(role);
                LOGGER.info("Created role: {}", roleEnum);
            } else {
                LOGGER.debug("Role already exists: {}", roleEnum);
            }
        }
        
        LOGGER.info("Roles initialization completed");
    }
}

