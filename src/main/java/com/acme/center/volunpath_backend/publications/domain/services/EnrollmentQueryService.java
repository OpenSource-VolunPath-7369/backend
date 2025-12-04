package com.acme.center.volunpath_backend.publications.domain.services;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Enrollment;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetEnrollmentsByPublicationIdQuery;

import java.util.List;

/**
 * Enrollment Query Service
 */
public interface EnrollmentQueryService {
    List<Enrollment> handle(GetEnrollmentsByPublicationIdQuery query);
    boolean isVolunteerEnrolled(Long publicationId, Long volunteerId);
}

