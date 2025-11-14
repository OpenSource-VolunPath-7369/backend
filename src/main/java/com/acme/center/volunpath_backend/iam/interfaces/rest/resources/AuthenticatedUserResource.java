package com.acme.center.volunpath_backend.iam.interfaces.rest.resources;

import java.util.List;

/**
 * Authenticated User Resource
 * DTO for authenticated user with token
 */
public record AuthenticatedUserResource(
    Long id,
    String username,
    String email,
    String name,
    String avatar,
    String token,
    List<String> roles
) {
}

