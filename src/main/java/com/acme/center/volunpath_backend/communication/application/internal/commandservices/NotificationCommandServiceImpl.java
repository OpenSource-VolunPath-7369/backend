package com.acme.center.volunpath_backend.communication.application.internal.commandservices;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Notification;
import com.acme.center.volunpath_backend.communication.domain.model.commands.CreateNotificationCommand;
import com.acme.center.volunpath_backend.communication.domain.services.NotificationCommandService;
import com.acme.center.volunpath_backend.communication.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Notification Command Service Implementation
 */
@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {
    private final NotificationRepository notificationRepository;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Optional<Notification> handle(CreateNotificationCommand command) {
        var notification = new Notification(
                command.userId(),
                command.title(),
                command.message(),
                command.type(),
                command.actionUrl()
        );

        notificationRepository.save(notification);
        return Optional.of(notification);
    }

    @Override
    public Optional<Notification> handle(Long notificationId) {
        var notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.markAsRead();
        notificationRepository.save(notification);
        return Optional.of(notification);
    }

    @Override
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsReadByUserId(userId);
    }

    @Override
    public void delete(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new RuntimeException("Notification not found");
        }
        notificationRepository.deleteById(notificationId);
    }
}

