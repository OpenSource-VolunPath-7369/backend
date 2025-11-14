package com.acme.center.volunpath_backend.volunteers.interfaces.rest.transform;

import com.acme.center.volunpath_backend.volunteers.domain.model.commands.CreateVolunteerCommand;
import com.acme.center.volunpath_backend.volunteers.interfaces.rest.resources.CreateVolunteerResource;

/**
 * Create Volunteer Command From Resource Assembler
 */
public class CreateVolunteerCommandFromResourceAssembler {
    public static CreateVolunteerCommand toCommandFromResource(CreateVolunteerResource resource) {
        return new CreateVolunteerCommand(
                resource.name(),
                resource.email(),
                resource.avatar(),
                resource.bio(),
                resource.location(),
                resource.userId(),
                resource.skills()
        );
    }
}

