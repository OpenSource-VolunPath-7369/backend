package com.acme.center.volunpath_backend.communication.domain.model.commands;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;

/**
 * Create Message Command
 */
public record CreateMessageCommand(
    Long senderId,
    String senderName,
    String senderIcon,
    Long recipientId,
    String content,
    Message.MessageType type,
    String senderOrganization
) {
}

