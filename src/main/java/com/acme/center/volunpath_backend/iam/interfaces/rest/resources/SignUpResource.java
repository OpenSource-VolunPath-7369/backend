package com.acme.center.volunpath_backend.iam.interfaces.rest.resources;

import java.util.List;

/**
 * Sign Up Resource
 * DTO for user registration
 */
public record SignUpResource(
    String username,
    String email,
    String password,
    String name,
    String avatar,
    List<String> roles
) {
}

