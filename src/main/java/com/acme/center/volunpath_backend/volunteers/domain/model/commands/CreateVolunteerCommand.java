package com.acme.center.volunpath_backend.volunteers.domain.model.commands;

import java.util.List;

/**
 * Create Volunteer Command
 */
public record CreateVolunteerCommand(
    String name,
    String email,
    String avatar,
    String bio,
    String location,
    Long userId,
    List<String> skills
) {
}

