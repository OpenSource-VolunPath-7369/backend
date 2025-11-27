package com.acme.center.volunpath_backend.communication.application.internal.commandservices;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;
import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Notification;
import com.acme.center.volunpath_backend.communication.domain.model.commands.CreateMessageCommand;
import com.acme.center.volunpath_backend.communication.domain.model.commands.CreateNotificationCommand;
import com.acme.center.volunpath_backend.communication.domain.services.MessageCommandService;
import com.acme.center.volunpath_backend.communication.domain.services.NotificationCommandService;
import com.acme.center.volunpath_backend.communication.infrastructure.persistence.jpa.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Message Command Service Implementation
 */
@Service
public class MessageCommandServiceImpl implements MessageCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageCommandServiceImpl.class);
    private final MessageRepository messageRepository;
    private final NotificationCommandService notificationCommandService;

    public MessageCommandServiceImpl(
            MessageRepository messageRepository,
            NotificationCommandService notificationCommandService) {
        this.messageRepository = messageRepository;
        this.notificationCommandService = notificationCommandService;
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
        LOGGER.info("Message created: ID={}, From={}, To={}", message.getId(), command.senderId(), command.recipientId());

        // Create notification for the recipient
        try {
            var notificationTitle = "Nuevo mensaje de " + command.senderName();
            var notificationMessage = command.content().length() > 100 
                    ? command.content().substring(0, 100) + "..." 
                    : command.content();
            var actionUrl = "/mensajes"; // URL to messages page
            
            var notificationCommand = new CreateNotificationCommand(
                    command.recipientId(),
                    notificationTitle,
                    notificationMessage,
                    Notification.NotificationType.NEW_MESSAGE,
                    actionUrl
            );
            
            notificationCommandService.handle(notificationCommand);
            LOGGER.info("Notification created for user: {}", command.recipientId());
        } catch (Exception e) {
            LOGGER.error("Error creating notification for message: {}", e.getMessage(), e);
            // Don't fail the message creation if notification fails
        }

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

