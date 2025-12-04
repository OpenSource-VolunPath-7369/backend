package com.acme.center.volunpath_backend.publications.domain.model.aggregates;

import com.acme.center.volunpath_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Enrollment aggregate root
 * Represents a volunteer enrollment in a publication
 */
@Getter
@Setter
@Entity
@Table(name = "enrollments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"publication_id", "volunteer_id"})
})
public class Enrollment extends AuditableAbstractAggregateRoot<Enrollment> {

    @NotNull
    @Column(name = "publication_id", nullable = false)
    private Long publicationId;

    @NotNull
    @Column(name = "volunteer_id", nullable = false)
    private Long volunteerId;

    @Size(max = 200)
    @Column(name = "volunteer_name")
    private String volunteerName;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EnrollmentStatus status = EnrollmentStatus.CONFIRMED;

    public Enrollment() {
        this.status = EnrollmentStatus.CONFIRMED;
    }

    public Enrollment(Long publicationId, Long volunteerId, String volunteerName) {
        this.publicationId = publicationId;
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.status = EnrollmentStatus.CONFIRMED;
    }

    public enum EnrollmentStatus {
        PENDING,
        CONFIRMED,
        CANCELLED
    }
}

