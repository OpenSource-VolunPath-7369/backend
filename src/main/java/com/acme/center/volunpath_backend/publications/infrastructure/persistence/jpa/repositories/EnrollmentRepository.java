package com.acme.center.volunpath_backend.publications.infrastructure.persistence.jpa.repositories;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByPublicationId(Long publicationId);
    Optional<Enrollment> findByPublicationIdAndVolunteerId(Long publicationId, Long volunteerId);
    boolean existsByPublicationIdAndVolunteerId(Long publicationId, Long volunteerId);
    long countByPublicationId(Long publicationId);
}

