package com.acme.center.volunpath_backend.communication.application.internal.commandservices;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;
import com.acme.center.volunpath_backend.communication.domain.model.commands.CreateMessageCommand;
import com.acme.center.volunpath_backend.communication.domain.services.MessageCommandService;
import com.acme.center.volunpath_backend.communication.infrastructure.persistence.jpa.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Message Command Service Implementation
 */
@Service
public class MessageCommandServiceImpl implements MessageCommandService {
    private final MessageRepository messageRepository;

    public MessageCommandServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Optional<Message> handle(CreateMessageCommand command) {
        var message = new Message(
                command.senderId(),
                command.senderName(),
                command.senderIcon(),
                command.recipientId(),
                command.content(),
                command.type(),
                command.senderOrganization()
        );

        messageRepository.save(message);
        return Optional.of(message);
    }

    @Override
    public Optional<Message> handle(Long messageId) {
        var message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.markAsRead();
        messageRepository.save(message);
        return Optional.of(message);
    }

    @Override
    public void delete(Long messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new RuntimeException("Message not found");
        }
        messageRepository.deleteById(messageId);
    }
}

