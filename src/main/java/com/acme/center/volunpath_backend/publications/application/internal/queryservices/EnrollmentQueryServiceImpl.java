package com.acme.center.volunpath_backend.publications.application.internal.queryservices;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Enrollment;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetEnrollmentsByPublicationIdQuery;
import com.acme.center.volunpath_backend.publications.domain.services.EnrollmentQueryService;
import com.acme.center.volunpath_backend.publications.infrastructure.persistence.jpa.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Enrollment Query Service Implementation
 */
@Service
public class EnrollmentQueryServiceImpl implements EnrollmentQueryService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentQueryServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<Enrollment> handle(GetEnrollmentsByPublicationIdQuery query) {
        return enrollmentRepository.findByPublicationId(query.publicationId());
    }

    @Override
    public boolean isVolunteerEnrolled(Long publicationId, Long volunteerId) {
        return enrollmentRepository.existsByPublicationIdAndVolunteerId(publicationId, volunteerId);
    }
}

