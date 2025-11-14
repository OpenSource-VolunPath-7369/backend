package com.acme.center.volunpath_backend.publications.domain.services;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetAllPublicationsQuery;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetPublicationByIdQuery;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetPublicationsByOrganizationIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Publication Query Service
 */
public interface PublicationQueryService {
    List<Publication> handle(GetAllPublicationsQuery query);
    Optional<Publication> handle(GetPublicationByIdQuery query);
    List<Publication> handle(GetPublicationsByOrganizationIdQuery query);
}

