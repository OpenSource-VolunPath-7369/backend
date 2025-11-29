package com.acme.center.volunpath_backend.publications.interfaces.rest.resources;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Publication Resource
 * DTO for publication information
 */
public record PublicationResource(
    Long id,
    String title,
    String description,
    String image,
    Long organizationId,
    Integer likes,
    Publication.PublicationStatus status,
    List<String> tags,
    String scheduledDate,
    String scheduledTime,
    String location,
    Integer maxVolunteers,
    Integer currentVolunteers,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

