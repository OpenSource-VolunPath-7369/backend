package com.acme.center.volunpath_backend.publications.interfaces.rest.resources;

/**
 * Create Enrollment Resource
 * DTO for creating an enrollment
 */
public record CreateEnrollmentResource(
    Long publicationId,
    Long volunteerId,
    String volunteerName
) {
}

