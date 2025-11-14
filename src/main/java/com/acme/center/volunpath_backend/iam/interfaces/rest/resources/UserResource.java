package com.acme.center.volunpath_backend.iam.interfaces.rest.resources;

import java.util.List;

/**
 * User Resource
 * DTO for user information
 */
public record UserResource(
    Long id,
    String username,
    String email,
    String name,
    String avatar,
    List<String> roles
) {
}

