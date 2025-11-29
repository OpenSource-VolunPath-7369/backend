package com.acme.center.volunpath_backend.iam.application.internal.commandservices;

import com.acme.center.volunpath_backend.iam.application.internal.outboundservices.hashing.HashingService;
import com.acme.center.volunpath_backend.iam.application.internal.outboundservices.tokens.TokenService;
import com.acme.center.volunpath_backend.iam.domain.model.aggregates.User;
import com.acme.center.volunpath_backend.iam.domain.model.commands.SignInCommand;
import com.acme.center.volunpath_backend.iam.domain.model.commands.SignUpCommand;
import com.acme.center.volunpath_backend.iam.domain.model.commands.UpdateUserCommand;
import com.acme.center.volunpath_backend.iam.domain.model.entities.Role;
import com.acme.center.volunpath_backend.iam.domain.services.UserCommandService;
import com.acme.center.volunpath_backend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.acme.center.volunpath_backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User Command Service Implementation
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommandServiceImpl.class);
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            HashingService hashingService,
            TokenService tokenService,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        // Try to find user by username first
        var user = userRepository.findByUsername(command.username());
        
        // If not found by username, try to find by email
        if (user.isEmpty()) {
            user = userRepository.findByEmail(command.username());
        }
        
        if (user.isEmpty()) {
            LOGGER.warn("Login attempt failed: User not found with identifier: {}", command.username());
            throw new RuntimeException("User not found");
        }
        
        if (!hashingService.matches(command.password(), user.get().getPassword())) {
            LOGGER.warn("Login attempt failed: Invalid password for user: {}", user.get().getUsername());
            throw new RuntimeException("Invalid password");
        }
        
        LOGGER.info("User authenticated successfully: {}", user.get().getUsername());
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        try {
            LOGGER.info("Processing sign-up for username: {}", command.username());
            
            if (userRepository.existsByUsername(command.username()))
                throw new RuntimeException("Username already exists");
            if (userRepository.existsByEmail(command.email()))
                throw new RuntimeException("Email already exists");
            
            LOGGER.debug("Checking roles: {}", command.roles());
            var roles = command.roles().stream()
                    .map(role -> {
                        // Get the Roles enum from the Role entity and find it in the database
                        var roleEnum = role.getName();
                        LOGGER.debug("Looking for role: {}", roleEnum);
                        var foundRole = roleRepository.findByName(roleEnum);
                        
                        // If role doesn't exist, create it
                        if (foundRole.isEmpty()) {
                            LOGGER.warn("Role {} not found in database, creating it now", roleEnum);
                            var newRole = new Role(roleEnum);
                            roleRepository.save(newRole);
                            LOGGER.info("Created missing role: {}", roleEnum);
                            return newRole;
                        }
                        
                        return foundRole.get();
                    })
                    .toList();
            
            LOGGER.debug("Found {} roles in database", roles.size());
            
            var user = new User(
                    command.username(),
                    command.email(),
                    hashingService.encode(command.password()),
                    command.name(),
                    command.avatar(),
                    roles
            );
            
            LOGGER.debug("Saving user to database");
            userRepository.save(user);
            LOGGER.info("User created successfully: {}", command.username());
            
            return userRepository.findByUsername(command.username());
        } catch (Exception e) {
            LOGGER.error("Error during sign-up: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        try {
            LOGGER.info("Processing update for user ID: {}", command.userId());
            
            var user = userRepository.findById(command.userId());
            if (user.isEmpty()) {
                LOGGER.warn("User not found with ID: {}", command.userId());
                throw new RuntimeException("User not found");
            }
            
            var existingUser = user.get();
            
            // Update only provided fields (non-null values)
            if (command.name() != null && !command.name().trim().isEmpty()) {
                existingUser.setName(command.name());
            }
            if (command.email() != null && !command.email().trim().isEmpty()) {
                // Check if email is already taken by another user
                var userWithEmail = userRepository.findByEmail(command.email());
                if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(command.userId())) {
                    throw new RuntimeException("Email already exists");
                }
                existingUser.setEmail(command.email());
            }
            if (command.avatar() != null) {
                existingUser.setAvatar(command.avatar());
            }
            
            LOGGER.debug("Saving updated user to database");
            userRepository.save(existingUser);
            LOGGER.info("User updated successfully: ID={}, Name={}", command.userId(), existingUser.getName());
            
            return userRepository.findById(command.userId());
        } catch (Exception e) {
            LOGGER.error("Error during user update: {}", e.getMessage(), e);
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }
}

