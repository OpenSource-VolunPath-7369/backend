package com.acme.center.volunpath_backend.iam.domain.model.commands;

import com.acme.center.volunpath_backend.iam.domain.model.entities.Role;

import java.util.List;

/**
 * Sign up command
 * Command to register a new user
 */
public record SignUpCommand(
    String username,
    String email,
    String password,
    String name,
    String avatar,
    List<Role> roles
) {
}

