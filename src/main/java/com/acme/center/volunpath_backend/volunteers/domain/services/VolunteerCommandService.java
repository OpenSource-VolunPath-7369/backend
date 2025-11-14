package com.acme.center.volunpath_backend.volunteers.domain.services;

import com.acme.center.volunpath_backend.volunteers.domain.model.aggregates.Volunteer;
import com.acme.center.volunpath_backend.volunteers.domain.model.commands.CreateVolunteerCommand;
import com.acme.center.volunpath_backend.volunteers.domain.model.commands.UpdateVolunteerCommand;

import java.util.Optional;

/**
 * Volunteer Command Service
 */
public interface VolunteerCommandService {
    Optional<Volunteer> handle(CreateVolunteerCommand command);
    Optional<Volunteer> handle(UpdateVolunteerCommand command);
    void handle(Long volunteerId); // Delete
}

