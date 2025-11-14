package com.acme.center.volunpath_backend.communication.interfaces.rest.transform;

import com.acme.center.volunpath_backend.communication.domain.model.commands.CreateNotificationCommand;
import com.acme.center.volunpath_backend.communication.interfaces.rest.resources.CreateNotificationResource;

/**
 * Create Notification Command From Resource Assembler
 */
public class CreateNotificationCommandFromResourceAssembler {
    public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource resource) {
        return new CreateNotificationCommand(
                resource.userId(),
                resource.title(),
                resource.message(),
                resource.type(),
                resource.actionUrl()
        );
    }
}

