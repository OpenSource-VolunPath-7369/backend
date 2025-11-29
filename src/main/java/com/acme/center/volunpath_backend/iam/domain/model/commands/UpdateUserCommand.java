package com.acme.center.volunpath_backend.iam.domain.model.commands;

/**
 * Update User Command
 * Command for updating an existing user
 */
public record UpdateUserCommand(
    Long userId,
    String name,
    String email,
    String avatar
) {
}

