package com.acme.center.volunpath_backend.volunteers.interfaces.rest.resources;

import java.util.List;

/**
 * Create Volunteer Resource
 * DTO for creating a volunteer
 */
public record CreateVolunteerResource(
    String name,
    String email,
    String avatar,
    String bio,
    String location,
    Long userId,
    List<String> skills
) {
}

