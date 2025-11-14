package com.acme.center.volunpath_backend.publications.application.internal.queryservices;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetAllPublicationsQuery;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetPublicationByIdQuery;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetPublicationsByOrganizationIdQuery;
import com.acme.center.volunpath_backend.publications.domain.services.PublicationQueryService;
import com.acme.center.volunpath_backend.publications.infrastructure.persistence.jpa.repositories.PublicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Publication Query Service Implementation
 */
@Service
public class PublicationQueryServiceImpl implements PublicationQueryService {
    private final PublicationRepository publicationRepository;

    public PublicationQueryServiceImpl(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Override
    public List<Publication> handle(GetAllPublicationsQuery query) {
        return publicationRepository.findAll();
    }

    @Override
    public Optional<Publication> handle(GetPublicationByIdQuery query) {
        return publicationRepository.findById(query.publicationId());
    }

    @Override
    public List<Publication> handle(GetPublicationsByOrganizationIdQuery query) {
        return publicationRepository.findByOrganizationId(query.organizationId());
    }
}

