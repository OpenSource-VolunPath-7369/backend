package com.acme.center.volunpath_backend.communication.interfaces.rest.transform;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Notification;
import com.acme.center.volunpath_backend.communication.interfaces.rest.resources.NotificationResource;

/**
 * Notification Resource From Entity Assembler
 */
public class NotificationResourceFromEntityAssembler {
    public static NotificationResource toResourceFromEntity(Notification notification) {
        return new NotificationResource(
                notification.getId(),
                notification.getUserId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getIsRead(),
                notification.getActionUrl(),
                notification.getCreatedAt(),
                notification.getUpdatedAt()
        );
    }
}

