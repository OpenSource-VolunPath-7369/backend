package com.acme.center.volunpath_backend.volunteers.domain.model.commands;

import java.util.List;

/**
 * Update Volunteer Command
 */
public record UpdateVolunteerCommand(
    Long volunteerId,
    String name,
    String email,
    String avatar,
    String bio,
    String location,
    List<String> skills
) {
}

