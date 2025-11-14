package com.acme.center.volunpath_backend.volunteers.interfaces.rest.resources;

import java.util.List;

/**
 * Volunteer Resource
 * DTO for volunteer information
 */
public record VolunteerResource(
    Long id,
    String name,
    String email,
    String avatar,
    String bio,
    String location,
    Long userId,
    List<String> skills
) {
}

