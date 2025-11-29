package com.acme.center.volunpath_backend.iam.interfaces.rest.resources;

/**
 * Update User Resource
 * DTO for updating a user
 */
public record UpdateUserResource(
    String name,
    String email,
    String avatar
) {
}

