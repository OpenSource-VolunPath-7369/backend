package com.acme.center.volunpath_backend.communication.domain.services;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Notification;
import com.acme.center.volunpath_backend.communication.domain.model.commands.CreateNotificationCommand;

import java.util.Optional;

/**
 * Notification Command Service
 */
public interface NotificationCommandService {
    Optional<Notification> handle(CreateNotificationCommand command);
    Optional<Notification> handle(Long notificationId); // Mark as read
    void markAllAsRead(Long userId);
    void delete(Long notificationId);
}

