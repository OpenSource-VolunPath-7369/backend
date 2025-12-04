package com.acme.center.volunpath_backend.publications.interfaces.rest.resources;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Enrollment;

import java.time.LocalDateTime;

/**
 * Enrollment Resource
 * DTO for enrollment information
 */
public record EnrollmentResource(
    Long id,
    Long publicationId,
    Long volunteerId,
    String volunteerName,
    Enrollment.EnrollmentStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

