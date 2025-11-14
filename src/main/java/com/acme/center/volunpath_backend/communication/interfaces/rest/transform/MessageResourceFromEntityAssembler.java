package com.acme.center.volunpath_backend.communication.interfaces.rest.transform;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;
import com.acme.center.volunpath_backend.communication.interfaces.rest.resources.MessageResource;

/**
 * Message Resource From Entity Assembler
 */
public class MessageResourceFromEntityAssembler {
    public static MessageResource toResourceFromEntity(Message message) {
        return new MessageResource(
                message.getId(),
                message.getSenderId(),
                message.getSenderName(),
                message.getSenderIcon(),
                message.getRecipientId(),
                message.getContent(),
                message.getIsRead(),
                message.getType(),
                message.getSenderOrganization(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );
    }
}

