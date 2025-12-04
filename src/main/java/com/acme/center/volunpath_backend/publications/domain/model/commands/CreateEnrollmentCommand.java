package com.acme.center.volunpath_backend.publications.domain.model.commands;

/**
 * Create Enrollment Command
 */
public record CreateEnrollmentCommand(
    Long publicationId,
    Long volunteerId,
    String volunteerName
) {
}

