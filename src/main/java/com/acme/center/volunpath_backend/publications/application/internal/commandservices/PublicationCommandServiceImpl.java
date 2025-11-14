package com.acme.center.volunpath_backend.publications.application.internal.commandservices;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;
import com.acme.center.volunpath_backend.publications.domain.model.commands.CreatePublicationCommand;
import com.acme.center.volunpath_backend.publications.domain.model.commands.UpdatePublicationCommand;
import com.acme.center.volunpath_backend.publications.domain.services.PublicationCommandService;
import com.acme.center.volunpath_backend.publications.infrastructure.persistence.jpa.repositories.PublicationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Publication Command Service Implementation
 */
@Service
public class PublicationCommandServiceImpl implements PublicationCommandService {
    private final PublicationRepository publicationRepository;

    public PublicationCommandServiceImpl(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Override
    public Optional<Publication> handle(CreatePublicationCommand command) {
        var publication = new Publication(
                command.title(),
                command.description(),
                command.image(),
                command.organizationId()
        );

        if (command.tags() != null) {
            publication.addTags(command.tags());
        }

        if (command.status() != null) {
            publication.setStatus(command.status());
        }

        publicationRepository.save(publication);
        return Optional.of(publication);
    }

    @Override
    public Optional<Publication> handle(UpdatePublicationCommand command) {
        var publication = publicationRepository.findById(command.publicationId())
                .orElseThrow(() -> new RuntimeException("Publication not found"));

        if (command.title() != null) publication.setTitle(command.title());
        if (command.description() != null) publication.setDescription(command.description());
        if (command.image() != null) publication.setImage(command.image());
        if (command.tags() != null) {
            publication.getTags().clear();
            publication.addTags(command.tags());
        }
        if (command.status() != null) {
            publication.setStatus(command.status());
        }

        publicationRepository.save(publication);
        return Optional.of(publication);
    }

    @Override
    public Optional<Publication> handle(Long publicationId) {
        var publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
        publication.addLike();
        publicationRepository.save(publication);
        return Optional.of(publication);
    }

    @Override
    public void delete(Long publicationId) {
        if (!publicationRepository.existsById(publicationId)) {
            throw new RuntimeException("Publication not found");
        }
        publicationRepository.deleteById(publicationId);
    }
}

