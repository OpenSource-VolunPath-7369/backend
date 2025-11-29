package com.acme.center.volunpath_backend.publications.interfaces.rest.transform;

import com.acme.center.volunpath_backend.publications.domain.model.commands.UpdatePublicationCommand;
import com.acme.center.volunpath_backend.publications.interfaces.rest.resources.UpdatePublicationResource;

/**
 * Update Publication Command From Resource Assembler
 */
public class UpdatePublicationCommandFromResourceAssembler {
    public static UpdatePublicationCommand toCommandFromResource(Long publicationId, UpdatePublicationResource resource) {
        return new UpdatePublicationCommand(
                publicationId,
                resource.title(),
                resource.description(),
                resource.image(),
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

