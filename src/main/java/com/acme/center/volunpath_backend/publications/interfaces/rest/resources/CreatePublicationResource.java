package com.acme.center.volunpath_backend.publications.interfaces.rest.resources;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;

import java.util.List;

/**
 * Create Publication Resource
 * DTO for creating a publication
 */
public record CreatePublicationResource(
    String title,
    String description,
    String image,
    Long organizationId,
    List<String> tags,
    Publication.PublicationStatus status
) {
}

