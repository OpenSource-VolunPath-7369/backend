package com.acme.center.volunpath_backend.communication.interfaces.rest.resources;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Notification;

/**
 * Create Notification Resource
 * DTO for creating a notification
 */
public record CreateNotificationResource(
    Long userId,
    String title,
    String message,
    Notification.NotificationType type,
    String actionUrl
) {
}

