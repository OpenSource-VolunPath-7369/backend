package com.acme.center.volunpath_backend.publications.interfaces.rest.transform;

import com.acme.center.volunpath_backend.publications.domain.model.commands.CreatePublicationCommand;
import com.acme.center.volunpath_backend.publications.interfaces.rest.resources.CreatePublicationResource;

/**
 * Create Publication Command From Resource Assembler
 */
public class CreatePublicationCommandFromResourceAssembler {
    public static CreatePublicationCommand toCommandFromResource(CreatePublicationResource resource) {
        return new CreatePublicationCommand(
                resource.title(),
                resource.description(),
                resource.image(),
                resource.organizationId(),
                resource.tags(),
                resource.status(),
                resource.scheduledDate(),
                resource.scheduledTime(),
                resource.location(),
                resource.maxVolunteers(),
                resource.currentVolunteers()
        );
    }
}

