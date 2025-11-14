package com.acme.center.volunpath_backend.iam.interfaces.rest.resources;

/**
 * Sign In Resource
 * DTO for user authentication
 */
public record SignInResource(
    String username,
    String password
) {
}

