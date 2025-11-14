package com.acme.center.volunpath_backend.communication.domain.model.commands;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Notification;

/**
 * Create Notification Command
 */
public record CreateNotificationCommand(
    Long userId,
    String title,
    String message,
    Notification.NotificationType type,
    String actionUrl
) {
}

