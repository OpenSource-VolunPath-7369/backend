package com.acme.center.volunpath_backend.iam.domain.model.commands;

/**
 * Sign in command
 * Command to authenticate a user
 */
public record SignInCommand(
    String username,
    String password
) {
}

