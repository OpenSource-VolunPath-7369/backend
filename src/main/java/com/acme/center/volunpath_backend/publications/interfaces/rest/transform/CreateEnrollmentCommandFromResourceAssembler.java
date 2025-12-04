package com.acme.center.volunpath_backend.publications.interfaces.rest.transform;

import com.acme.center.volunpath_backend.publications.domain.model.commands.CreateEnrollmentCommand;
import com.acme.center.volunpath_backend.publications.interfaces.rest.resources.CreateEnrollmentResource;

/**
 * Create Enrollment Command From Resource Assembler
 */
public class CreateEnrollmentCommandFromResourceAssembler {
    public static CreateEnrollmentCommand toCommandFromResource(CreateEnrollmentResource resource) {
        return new CreateEnrollmentCommand(
                resource.publicationId(),
                resource.volunteerId(),
                resource.volunteerName()
        );
    }
}

