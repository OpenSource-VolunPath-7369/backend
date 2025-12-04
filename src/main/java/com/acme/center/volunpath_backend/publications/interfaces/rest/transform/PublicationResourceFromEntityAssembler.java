package com.acme.center.volunpath_backend.publications.interfaces.rest.transform;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Publication;
import com.acme.center.volunpath_backend.publications.interfaces.rest.resources.PublicationResource;

/**
 * Publication Resource From Entity Assembler
 */
public class PublicationResourceFromEntityAssembler {
    public static PublicationResource toResourceFromEntity(Publication publication) {
        return new PublicationResource(
                publication.getId(),
                publication.getTitle(),
                publication.getDescription(),
                publication.getImage(),
                publication.getOrganizationId(),
                publication.getLikes(),
                publication.getStatus(),
                publication.getTags(),
                publication.getScheduledDate(),
                publication.getScheduledTime(),
                publication.getLocation(),
                publication.getMaxVolunteers(),
                publication.getCurrentVolunteers(),
                publication.getCreatedAt(),
                publication.getUpdatedAt()
        );
    }
    
    public static PublicationResource toResourceFromEntity(Publication publication, Integer actualCurrentVolunteers) {
        return new PublicationResource(
                publication.getId(),
                publication.getTitle(),
                publication.getDescription(),
                publication.getImage(),
                publication.getOrganizationId(),
                publication.getLikes(),
                publication.getStatus(),
                publication.getTags(),
                publication.getScheduledDate(),
                publication.getScheduledTime(),
                publication.getLocation(),
                publication.getMaxVolunteers(),
                actualCurrentVolunteers != null ? actualCurrentVolunteers : publication.getCurrentVolunteers(),
                publication.getCreatedAt(),
                publication.getUpdatedAt()
        );
    }
}

