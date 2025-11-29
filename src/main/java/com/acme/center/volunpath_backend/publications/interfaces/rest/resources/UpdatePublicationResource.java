package com.acme.center.volunpath_backend.publications.interfaces.rest.resources;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;

import java.util.List;

/**
 * Update Publication Resource
 * DTO for updating a publication
 */
public record UpdatePublicationResource(
    String title,
    String description,
    String image,
    Long organizationId,
    List<String> tags,
    Publication.PublicationStatus status,
    String scheduledDate,
    String scheduledTime,
    String location,
    Integer maxVolunteers,
    Integer currentVolunteers
) {
}

