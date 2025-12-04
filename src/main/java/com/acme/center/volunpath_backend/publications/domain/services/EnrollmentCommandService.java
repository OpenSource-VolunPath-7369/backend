package com.acme.center.volunpath_backend.publications.domain.services;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Enrollment;
import com.acme.center.volunpath_backend.publications.domain.model.commands.CreateEnrollmentCommand;

import java.util.Optional;

/**
 * Enrollment Command Service
 */
public interface EnrollmentCommandService {
    Optional<Enrollment> handle(CreateEnrollmentCommand command);
    void delete(Long enrollmentId);
}

