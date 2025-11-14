package com.acme.center.volunpath_backend.communication.domain.model.aggregates;

import com.acme.center.volunpath_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Notification aggregate root
 * Represents a notification for a user in the Volunpath system
 */
@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification extends AuditableAbstractAggregateRoot<Notification> {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    @Size(max = 1000)
    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private NotificationType type = NotificationType.GENERAL;

    private Boolean isRead = false;

    @Size(max = 500)
    @Column(name = "action_url")
    private String actionUrl;

    public Notification() {
        this.isRead = false;
        this.type = NotificationType.GENERAL;
    }

    public Notification(Long userId, String title, String message, NotificationType type, String actionUrl) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.actionUrl = actionUrl;
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public void markAsUnread() {
        this.isRead = false;
    }

    public boolean isUnread() {
        return !isRead;
    }

    public enum NotificationType {
        NEW_ACTIVITY,
        NEW_MESSAGE,
        ACTIVITY_CONFIRMED,
        ACTIVITY_CANCELLED,
        GENERAL
    }
}

