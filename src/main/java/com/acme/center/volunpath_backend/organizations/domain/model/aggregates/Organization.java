package com.acme.center.volunpath_backend.organizations.domain.model.aggregates;

import com.acme.center.volunpath_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Organization aggregate root
 * Represents an organization in the Volunpath system
 */
@Getter
@Setter
@Entity
@Table(name = "organizations")
public class Organization extends AuditableAbstractAggregateRoot<Organization> {

    @NotBlank
    @Size(max = 200)
    private String name;

    @Size(max = 100000)
    @Column(columnDefinition = "LONGTEXT")
    private String logo;

    @Size(max = 2000)
    private String description;

    @Size(max = 500)
    private String website;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Size(max = 50)
    private String phone;

    @Size(max = 500)
    private String address;

    private Integer foundedYear;

    private Integer volunteerCount = 0;

    private Double rating = 0.0;

    @ElementCollection
    @CollectionTable(name = "organization_categories", joinColumns = @JoinColumn(name = "organization_id"))
    @Column(name = "category")
    private List<String> categories = new ArrayList<>();

    private Boolean isVerified = false;

    @Embedded
    private SocialMedia socialMedia;

    @Column(name = "user_id", unique = true)
    private Long userId; // Reference to User in IAM context

    public Organization() {
        this.categories = new ArrayList<>();
        this.volunteerCount = 0;
        this.rating = 0.0;
        this.isVerified = false;
        this.socialMedia = new SocialMedia();
    }

    public Organization(String name, String email, String logo, String description, String website,
                       String phone, String address, Integer foundedYear, Long userId) {
        this.name = name;
        this.email = email;
        this.logo = logo;
        this.description = description;
        this.website = website;
        this.phone = phone;
        this.address = address;
        this.foundedYear = foundedYear;
        this.userId = userId;
        this.categories = new ArrayList<>();
        this.volunteerCount = 0;
        this.rating = 0.0;
        this.isVerified = false;
        this.socialMedia = new SocialMedia();
    }

    public Organization addCategory(String category) {
        if (category != null && !category.trim().isEmpty() && !this.categories.contains(category)) {
            this.categories.add(category);
        }
        return this;
    }

    public Organization addCategories(List<String> categories) {
        if (categories != null) {
            categories.forEach(this::addCategory);
        }
        return this;
    }

    @Embeddable
    @Getter
    @Setter
    public static class SocialMedia {
        @Size(max = 500)
        private String facebook;

        @Size(max = 500)
        private String instagram;

        @Size(max = 500)
        private String twitter;

        public SocialMedia() {
        }
    }
}

