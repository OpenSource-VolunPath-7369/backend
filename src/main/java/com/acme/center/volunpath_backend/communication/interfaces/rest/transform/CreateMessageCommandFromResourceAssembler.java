package com.acme.center.volunpath_backend.communication.interfaces.rest.transform;

import com.acme.center.volunpath_backend.communication.domain.model.commands.CreateMessageCommand;
import com.acme.center.volunpath_backend.communication.interfaces.rest.resources.CreateMessageResource;

/**
 * Create Message Command From Resource Assembler
 */
public class CreateMessageCommandFromResourceAssembler {
    public static CreateMessageCommand toCommandFromResource(CreateMessageResource resource) {
        return new CreateMessageCommand(
                resource.senderId(),
                resource.senderName(),
                resource.senderIcon(),
                resource.recipientId(),
                resource.content(),
                resource.type(),
                resource.senderOrganization()
        );
    }
}

