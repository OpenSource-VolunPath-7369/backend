package com.acme.center.volunpath_backend.communication.application.internal.queryservices;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Notification;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetNotificationByIdQuery;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetNotificationsByUserIdQuery;
import com.acme.center.volunpath_backend.communication.domain.services.NotificationQueryService;
import com.acme.center.volunpath_backend.communication.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Notification Query Service Implementation
 */
@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        return notificationRepository.findById(query.notificationId());
    }

    @Override
    public List<Notification> handle(GetNotificationsByUserIdQuery query) {
        return notificationRepository.findByUserId(query.userId());
    }
}

