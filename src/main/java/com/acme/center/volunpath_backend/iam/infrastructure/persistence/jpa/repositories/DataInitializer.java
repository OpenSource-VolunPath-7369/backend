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
        
        try {
            for (Roles roleEnum : Roles.values()) {
                try {
                    if (!roleRepository.existsByName(roleEnum)) {
                        Role role = new Role(roleEnum);
                        roleRepository.save(role);
                        LOGGER.info("Created role: {}", roleEnum);
                    } else {
                        LOGGER.debug("Role already exists: {}", roleEnum);
                    }
                } catch (Exception e) {
                    LOGGER.error("Error creating role {}: {}", roleEnum, e.getMessage(), e);
                    // Try to find the role directly
                    var existingRole = roleRepository.findByName(roleEnum);
                    if (existingRole.isEmpty()) {
                        LOGGER.warn("Role {} does not exist and could not be created", roleEnum);
                    }
                }
            }
            
            // Verify all roles exist
            for (Roles roleEnum : Roles.values()) {
                var role = roleRepository.findByName(roleEnum);
                if (role.isEmpty()) {
                    LOGGER.error("CRITICAL: Role {} is missing from database after initialization!", roleEnum);
                } else {
                    LOGGER.info("Verified role exists: {}", roleEnum);
                }
            }
            
            LOGGER.info("Roles initialization completed");
        } catch (Exception e) {
            LOGGER.error("Fatal error during roles initialization: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize roles", e);
        }
    }
}

