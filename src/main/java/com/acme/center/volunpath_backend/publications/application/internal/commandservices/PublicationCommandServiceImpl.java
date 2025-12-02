package com.acme.center.volunpath_backend.publications.application.internal.commandservices;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;
import com.acme.center.volunpath_backend.publications.domain.model.commands.CreatePublicationCommand;
import com.acme.center.volunpath_backend.publications.domain.model.commands.UpdatePublicationCommand;
import com.acme.center.volunpath_backend.publications.domain.services.PublicationCommandService;
import com.acme.center.volunpath_backend.publications.infrastructure.persistence.jpa.repositories.PublicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Publication Command Service Implementation
 */
@Service
public class PublicationCommandServiceImpl implements PublicationCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicationCommandServiceImpl.class);
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

        // Set new fields
        if (command.scheduledDate() != null && !command.scheduledDate().trim().isEmpty()) {
            String dateStr = command.scheduledDate().trim();
            validateFutureDate(dateStr);
            publication.setScheduledDate(dateStr);
            LOGGER.info("Setting scheduledDate: {}", command.scheduledDate());
        } else {
            LOGGER.warn("scheduledDate is null or empty in CreatePublicationCommand");
        }
        if (command.scheduledTime() != null && !command.scheduledTime().trim().isEmpty()) {
            publication.setScheduledTime(command.scheduledTime().trim());
            LOGGER.info("Setting scheduledTime: {}", command.scheduledTime());
        } else {
            LOGGER.warn("scheduledTime is null or empty in CreatePublicationCommand");
        }
        if (command.location() != null && !command.location().trim().isEmpty()) {
            publication.setLocation(command.location().trim());
            LOGGER.info("Setting location: {}", command.location());
        }
        if (command.maxVolunteers() != null) {
            publication.setMaxVolunteers(command.maxVolunteers());
        }
        if (command.currentVolunteers() != null) {
            publication.setCurrentVolunteers(command.currentVolunteers());
        }

        LOGGER.info("Saving publication with scheduledDate={}, scheduledTime={}, location={}", 
            publication.getScheduledDate(), publication.getScheduledTime(), publication.getLocation());
        publicationRepository.save(publication);
        LOGGER.info("Publication saved with ID: {}, scheduledDate: {}, scheduledTime: {}", 
            publication.getId(), publication.getScheduledDate(), publication.getScheduledTime());
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
        if (command.scheduledDate() != null && !command.scheduledDate().trim().isEmpty()) {
            String dateStr = command.scheduledDate().trim();
            validateFutureDate(dateStr);
            publication.setScheduledDate(dateStr);
            LOGGER.info("Updating scheduledDate to: {}", command.scheduledDate());
        } else if (command.scheduledDate() != null) {
            LOGGER.warn("scheduledDate is empty string in UpdatePublicationCommand, keeping existing value");
        }
        if (command.scheduledTime() != null && !command.scheduledTime().trim().isEmpty()) {
            publication.setScheduledTime(command.scheduledTime().trim());
            LOGGER.info("Updating scheduledTime to: {}", command.scheduledTime());
        } else if (command.scheduledTime() != null) {
            LOGGER.warn("scheduledTime is empty string in UpdatePublicationCommand, keeping existing value");
        }
        if (command.location() != null && !command.location().trim().isEmpty()) {
            publication.setLocation(command.location().trim());
            LOGGER.info("Updating location to: {}", command.location());
        }
        if (command.maxVolunteers() != null) {
            publication.setMaxVolunteers(command.maxVolunteers());
        }
        if (command.currentVolunteers() != null) {
            publication.setCurrentVolunteers(command.currentVolunteers());
        }

        LOGGER.info("Saving updated publication ID={} with scheduledDate={}, scheduledTime={}, location={}", 
            publication.getId(), publication.getScheduledDate(), publication.getScheduledTime(), publication.getLocation());
        publicationRepository.save(publication);
        LOGGER.info("Publication updated successfully. ID: {}, scheduledDate: {}, scheduledTime: {}", 
            publication.getId(), publication.getScheduledDate(), publication.getScheduledTime());
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

    /**
     * Validates that the scheduled date is in the future
     * @param dateStr Date string in format YYYY-MM-DD
     * @throws IllegalArgumentException if the date is in the past or invalid format
     */
    private void validateFutureDate(String dateStr) {
        try {
            LocalDate scheduledDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate today = LocalDate.now();
            
            if (scheduledDate.isBefore(today) || scheduledDate.isEqual(today)) {
                throw new IllegalArgumentException("La fecha debe ser mayor a la actual. Fecha proporcionada: " + dateStr);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Se espera YYYY-MM-DD. Fecha proporcionada: " + dateStr, e);
        }
    }
}

