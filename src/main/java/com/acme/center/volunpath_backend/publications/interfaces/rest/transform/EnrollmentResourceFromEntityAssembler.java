package com.acme.center.volunpath_backend.publications.interfaces.rest.transform;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Enrollment;
import com.acme.center.volunpath_backend.publications.interfaces.rest.resources.EnrollmentResource;

/**
 * Enrollment Resource From Entity Assembler
 */
public class EnrollmentResourceFromEntityAssembler {
    public static EnrollmentResource toResourceFromEntity(Enrollment enrollment) {
        return new EnrollmentResource(
                enrollment.getId(),
                enrollment.getPublicationId(),
                enrollment.getVolunteerId(),
                enrollment.getVolunteerName(),
                enrollment.getStatus(),
                enrollment.getCreatedAt(),
                enrollment.getUpdatedAt()
        );
    }
}

