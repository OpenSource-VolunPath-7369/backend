package com.acme.center.volunpath_backend.publications.domain.services;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;
import com.acme.center.volunpath_backend.publications.domain.model.commands.CreatePublicationCommand;
import com.acme.center.volunpath_backend.publications.domain.model.commands.UpdatePublicationCommand;

import java.util.Optional;

/**
 * Publication Command Service
 */
public interface PublicationCommandService {
    Optional<Publication> handle(CreatePublicationCommand command);
    Optional<Publication> handle(UpdatePublicationCommand command);
    Optional<Publication> handle(Long publicationId); // Like/Unlike
    void delete(Long publicationId);
}

