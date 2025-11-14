package com.acme.center.volunpath_backend.publications.infrastructure.persistence.jpa.repositories;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByOrganizationId(Long organizationId);
    List<Publication> findByOrganizationIdAndStatus(Long organizationId, Publication.PublicationStatus status);
    List<Publication> findByStatus(Publication.PublicationStatus status);
}

