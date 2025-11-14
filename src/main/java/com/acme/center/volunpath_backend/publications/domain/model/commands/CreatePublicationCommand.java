package com.acme.center.volunpath_backend.publications.domain.model.commands;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;

import java.util.List;

/**
 * Create Publication Command
 */
public record CreatePublicationCommand(
    String title,
    String description,
    String image,
    Long organizationId,
    List<String> tags,
    Publication.PublicationStatus status
) {
}

