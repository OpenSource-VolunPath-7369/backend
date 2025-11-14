package com.acme.center.volunpath_backend.publications.domain.model.commands;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;

import java.util.List;

/**
 * Update Publication Command
 */
public record UpdatePublicationCommand(
    Long publicationId,
    String title,
    String description,
    String image,
    List<String> tags,
    Publication.PublicationStatus status
) {
}

