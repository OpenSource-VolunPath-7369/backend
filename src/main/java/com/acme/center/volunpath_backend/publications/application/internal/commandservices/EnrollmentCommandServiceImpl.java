package com.acme.center.volunpath_backend.publications.application.internal.commandservices;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Enrollment;
import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;
import com.acme.center.volunpath_backend.publications.domain.model.commands.CreateEnrollmentCommand;
import com.acme.center.volunpath_backend.publications.domain.services.EnrollmentCommandService;
import com.acme.center.volunpath_backend.publications.infrastructure.persistence.jpa.repositories.EnrollmentRepository;
import com.acme.center.volunpath_backend.publications.infrastructure.persistence.jpa.repositories.PublicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Enrollment Command Service Implementation
 */
@Service
public class EnrollmentCommandServiceImpl implements EnrollmentCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentCommandServiceImpl.class);
    private final EnrollmentRepository enrollmentRepository;
    private final PublicationRepository publicationRepository;

    public EnrollmentCommandServiceImpl(
            EnrollmentRepository enrollmentRepository,
            PublicationRepository publicationRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.publicationRepository = publicationRepository;
    }

    @Override
    @Transactional
    public Optional<Enrollment> handle(CreateEnrollmentCommand command) {
        // Check if volunteer is already enrolled
        if (enrollmentRepository.existsByPublicationIdAndVolunteerId(
                command.publicationId(), command.volunteerId())) {
            throw new IllegalArgumentException("El voluntario ya está registrado en esta publicación");
        }

        // Get publication to check available spots
        Publication publication = publicationRepository.findById(command.publicationId())
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        // Check if there are available spots
        if (publication.getCurrentVolunteers() >= publication.getMaxVolunteers()) {
            throw new IllegalArgumentException("No hay cupos disponibles");
        }

        // Create enrollment
        Enrollment enrollment = new Enrollment(
                command.publicationId(),
                command.volunteerId(),
                command.volunteerName()
        );

        enrollment = enrollmentRepository.save(enrollment);
        LOGGER.info("Enrollment created: ID={}, PublicationId={}, VolunteerId={}", 
                enrollment.getId(), enrollment.getPublicationId(), enrollment.getVolunteerId());

        // Update publication counter
        publication.setCurrentVolunteers(publication.getCurrentVolunteers() + 1);
        publicationRepository.save(publication);
        LOGGER.info("Publication counter updated: PublicationId={}, CurrentVolunteers={}", 
                publication.getId(), publication.getCurrentVolunteers());

        return Optional.of(enrollment);
    }

    @Override
    @Transactional
    public void delete(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment no encontrado"));

        // Get publication to update counter
        Publication publication = publicationRepository.findById(enrollment.getPublicationId())
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        // Delete enrollment
        enrollmentRepository.deleteById(enrollmentId);
        LOGGER.info("Enrollment deleted: ID={}", enrollmentId);

        // Update publication counter
        if (publication.getCurrentVolunteers() > 0) {
            publication.setCurrentVolunteers(publication.getCurrentVolunteers() - 1);
            publicationRepository.save(publication);
            LOGGER.info("Publication counter updated: PublicationId={}, CurrentVolunteers={}", 
                    publication.getId(), publication.getCurrentVolunteers());
        }
    }
}

