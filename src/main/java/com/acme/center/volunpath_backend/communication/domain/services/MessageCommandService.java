package com.acme.center.volunpath_backend.communication.domain.services;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;
import com.acme.center.volunpath_backend.communication.domain.model.commands.CreateMessageCommand;

import java.util.Optional;

/**
 * Message Command Service
 */
public interface MessageCommandService {
    Optional<Message> handle(CreateMessageCommand command);
    Optional<Message> handle(Long messageId); // Mark as read
    void delete(Long messageId);
}

