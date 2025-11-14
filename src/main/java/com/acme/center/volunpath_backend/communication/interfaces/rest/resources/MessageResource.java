package com.acme.center.volunpath_backend.communication.interfaces.rest.resources;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;

import java.time.LocalDateTime;

/**
 * Message Resource
 * DTO for message information
 */
public record MessageResource(
    Long id,
    Long senderId,
    String senderName,
    String senderIcon,
    Long recipientId,
    String content,
    Boolean isRead,
    Message.MessageType type,
    String senderOrganization,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

