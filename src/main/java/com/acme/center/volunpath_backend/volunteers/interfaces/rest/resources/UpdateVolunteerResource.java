package com.acme.center.volunpath_backend.volunteers.interfaces.rest.resources;

import java.util.List;

/**
 * Update Volunteer Resource
 * DTO for updating a volunteer
 */
public record UpdateVolunteerResource(
    String name,
    String email,
    String avatar,
    String bio,
    String location,
    List<String> skills
) {
}

