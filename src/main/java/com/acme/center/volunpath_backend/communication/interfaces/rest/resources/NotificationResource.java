package com.acme.center.volunpath_backend.communication.interfaces.rest.resources;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Notification;

import java.time.LocalDateTime;

/**
 * Notification Resource
 * DTO for notification information
 */
public record NotificationResource(
    Long id,
    Long userId,
    String title,
    String message,
    Notification.NotificationType type,
    Boolean isRead,
    String actionUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

