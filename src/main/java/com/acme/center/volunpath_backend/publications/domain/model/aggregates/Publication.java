package com.acme.center.volunpath_backend.publications.domain.model.aggregates;

import com.acme.center.volunpath_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Publication aggregate root
 * Represents a publication in the Volunpath system
 */
@Getter
@Setter
@Entity
@Table(name = "publications")
public class Publication extends AuditableAbstractAggregateRoot<Publication> {

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    @Size(max = 2000)
    @Column(columnDefinition = "TEXT")
    private String description;

    @Size(max = 100000)
    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    private Integer likes = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private PublicationStatus status = PublicationStatus.DRAFT;

    @ElementCollection
    @CollectionTable(name = "publication_tags", joinColumns = @JoinColumn(name = "publication_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @Size(max = 50)
    @Column(name = "scheduled_date")
    private String scheduledDate; // Format: YYYY-MM-DD

    @Size(max = 10)
    @Column(name = "scheduled_time")
    private String scheduledTime; // Format: HH:MM

    @Size(max = 200)
    @Column(name = "location")
    private String location;

    @Column(name = "max_volunteers")
    private Integer maxVolunteers = 0;

    @Column(name = "current_volunteers")
    private Integer currentVolunteers = 0;

    public Publication() {
        this.likes = 0;
        this.status = PublicationStatus.DRAFT;
        this.tags = new ArrayList<>();
        this.maxVolunteers = 0;
        this.currentVolunteers = 0;
    }

    public Publication(String title, String description, String image, Long organizationId) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.organizationId = organizationId;
        this.likes = 0;
        this.status = PublicationStatus.DRAFT;
        this.tags = new ArrayList<>();
    }

    public Publication addLike() {
        this.likes++;
        return this;
    }

    public Publication removeLike() {
        if (this.likes > 0) {
            this.likes--;
        }
        return this;
    }

    public Publication addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty() && !this.tags.contains(tag)) {
            this.tags.add(tag);
        }
        return this;
    }

    public Publication addTags(List<String> tags) {
        if (tags != null) {
            tags.forEach(this::addTag);
        }
        return this;
    }

    public enum PublicationStatus {
        DRAFT,
        PUBLISHED,
        ARCHIVED
    }
}

