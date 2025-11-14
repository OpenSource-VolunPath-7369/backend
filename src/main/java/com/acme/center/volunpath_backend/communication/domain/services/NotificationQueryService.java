package com.acme.center.volunpath_backend.communication.domain.services;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Notification;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetNotificationByIdQuery;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetNotificationsByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Notification Query Service
 */
public interface NotificationQueryService {
    Optional<Notification> handle(GetNotificationByIdQuery query);
    List<Notification> handle(GetNotificationsByUserIdQuery query);
}

