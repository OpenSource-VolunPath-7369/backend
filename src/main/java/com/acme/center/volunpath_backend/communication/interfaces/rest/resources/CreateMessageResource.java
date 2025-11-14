package com.acme.center.volunpath_backend.communication.interfaces.rest.resources;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;

/**
 * Create Message Resource
 * DTO for creating a message
 */
public record CreateMessageResource(
    Long senderId,
    String senderName,
    String senderIcon,
    Long recipientId,
    String content,
    Message.MessageType type,
    String senderOrganization
) {
}

