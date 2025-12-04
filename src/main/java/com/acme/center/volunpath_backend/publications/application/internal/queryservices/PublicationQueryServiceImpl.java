package com.acme.center.volunpath_backend.publications.application.internal.queryservices;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetAllPublicationsQuery;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetPublicationByIdQuery;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetPublicationsByOrganizationIdQuery;
import com.acme.center.volunpath_backend.publications.domain.services.PublicationQueryService;
import com.acme.center.volunpath_backend.publications.infrastructure.persistence.jpa.repositories.EnrollmentRepository;
import com.acme.center.volunpath_backend.publications.infrastructure.persistence.jpa.repositories.PublicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Publication Query Service Implementation
 */
@Service
public class PublicationQueryServiceImpl implements PublicationQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicationQueryServiceImpl.class);
    private final PublicationRepository publicationRepository;
    private final EnrollmentRepository enrollmentRepository;

    public PublicationQueryServiceImpl(
            PublicationRepository publicationRepository,
            EnrollmentRepository enrollmentRepository) {
        this.publicationRepository = publicationRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Publication> handle(GetAllPublicationsQuery query) {
        List<Publication> publications = publicationRepository.findAll();
        // Sync currentVolunteers with actual enrollment count
        syncCurrentVolunteers(publications);
        return publications;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Publication> handle(GetPublicationByIdQuery query) {
        Optional<Publication> publicationOpt = publicationRepository.findById(query.publicationId());
        if (publicationOpt.isPresent()) {
            Publication publication = publicationOpt.get();
            syncCurrentVolunteersForPublication(publication);
        }
        return publicationOpt;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Publication> handle(GetPublicationsByOrganizationIdQuery query) {
        List<Publication> publications = publicationRepository.findByOrganizationId(query.organizationId());
        // Sync currentVolunteers with actual enrollment count
        syncCurrentVolunteers(publications);
        return publications;
    }

    /**
     * Sync currentVolunteers for a list of publications with actual enrollment count
     */
    private void syncCurrentVolunteers(List<Publication> publications) {
        for (Publication publication : publications) {
            syncCurrentVolunteersForPublication(publication);
        }
    }

    /**
     * Sync currentVolunteers for a single publication with actual enrollment count
     */
    private void syncCurrentVolunteersForPublication(Publication publication) {
        if (publication.getId() != null) {
            try {
                long actualCount = enrollmentRepository.countByPublicationId(publication.getId());
                int storedCount = publication.getCurrentVolunteers() != null ? publication.getCurrentVolunteers() : 0;
                
                // Only update if there's a discrepancy (to avoid unnecessary writes)
                if (actualCount != storedCount) {
                    LOGGER.info("Syncing currentVolunteers for PublicationId={}: stored={}, actual={}", 
                            publication.getId(), storedCount, actualCount);
                    publication.setCurrentVolunteers((int) actualCount);
                    // Save the synced value back to DB
                    publicationRepository.save(publication);
                }
            } catch (Exception e) {
                LOGGER.error("Error syncing currentVolunteers for PublicationId={}: {}", 
                        publication.getId(), e.getMessage());
            }
        }
    }
}

