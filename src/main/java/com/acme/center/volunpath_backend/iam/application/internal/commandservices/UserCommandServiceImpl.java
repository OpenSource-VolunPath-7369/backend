package com.acme.center.volunpath_backend.iam.application.internal.commandservices;

import com.acme.center.volunpath_backend.iam.application.internal.outboundservices.hashing.HashingService;
import com.acme.center.volunpath_backend.iam.application.internal.outboundservices.tokens.TokenService;
import com.acme.center.volunpath_backend.iam.domain.model.aggregates.User;
import com.acme.center.volunpath_backend.iam.domain.model.commands.SignInCommand;
import com.acme.center.volunpath_backend.iam.domain.model.commands.SignUpCommand;
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
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
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
                        return roleRepository.findByName(roleEnum)
                                .orElseThrow(() -> new RuntimeException("Role name not found: " + roleEnum));
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
}

