package com.acme.center.volunpath_backend.volunteers.interfaces.rest.transform;

import com.acme.center.volunpath_backend.volunteers.domain.model.commands.UpdateVolunteerCommand;
import com.acme.center.volunpath_backend.volunteers.interfaces.rest.resources.UpdateVolunteerResource;

/**
 * Update Volunteer Command From Resource Assembler
 */
public class UpdateVolunteerCommandFromResourceAssembler {
    public static UpdateVolunteerCommand toCommandFromResource(Long volunteerId, UpdateVolunteerResource resource) {
        return new UpdateVolunteerCommand(
                volunteerId,
                resource.name(),
                resource.email(),
                resource.avatar(),
                resource.bio(),
                resource.location(),
                resource.skills()
        );
    }
}

