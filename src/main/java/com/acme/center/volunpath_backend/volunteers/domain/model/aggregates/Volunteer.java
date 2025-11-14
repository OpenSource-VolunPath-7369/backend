package com.acme.center.volunpath_backend.volunteers.domain.model.aggregates;

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
 * Volunteer aggregate root
 * Represents a volunteer in the Volunpath system
 */
@Getter
@Setter
@Entity
@Table(name = "volunteers")
public class Volunteer extends AuditableAbstractAggregateRoot<Volunteer> {

    @NotBlank
    @Size(max = 200)
    private String name;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Size(max = 100000)
    @Column(columnDefinition = "LONGTEXT")
    private String avatar;

    @Size(max = 1000)
    private String bio;

    @ElementCollection
    @CollectionTable(name = "volunteer_skills", joinColumns = @JoinColumn(name = "volunteer_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();

    @Size(max = 200)
    private String location;

    @Column(name = "user_id", unique = true)
    private Long userId; // Reference to User in IAM context

    public Volunteer() {
        this.skills = new ArrayList<>();
    }

    public Volunteer(String name, String email, String avatar, String bio, String location, Long userId) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.bio = bio;
        this.location = location;
        this.userId = userId;
        this.skills = new ArrayList<>();
    }

    public Volunteer addSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty() && !this.skills.contains(skill)) {
            this.skills.add(skill);
        }
        return this;
    }

    public Volunteer addSkills(List<String> skills) {
        if (skills != null) {
            skills.forEach(this::addSkill);
        }
        return this;
    }

    public Volunteer removeSkill(String skill) {
        this.skills.remove(skill);
        return this;
    }
}

