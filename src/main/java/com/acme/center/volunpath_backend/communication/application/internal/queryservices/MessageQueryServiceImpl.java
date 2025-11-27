package com.acme.center.volunpath_backend.communication.application.internal.queryservices;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetMessageByIdQuery;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetMessagesByUserIdQuery;
import com.acme.center.volunpath_backend.communication.domain.services.MessageQueryService;
import com.acme.center.volunpath_backend.communication.infrastructure.persistence.jpa.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Message Query Service Implementation
 */
@Service
public class MessageQueryServiceImpl implements MessageQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageQueryServiceImpl.class);
    private final MessageRepository messageRepository;

    public MessageQueryServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Optional<Message> handle(GetMessageByIdQuery query) {
        return messageRepository.findById(query.messageId());
    }

    @Override
    public List<Message> handle(GetMessagesByUserIdQuery query) {
        LOGGER.info("Querying messages for userId: {}", query.userId());
        
        // Get messages where user is recipient or sender
        List<Message> messages = messageRepository.findByRecipientIdOrSenderId(query.userId(), query.userId());
        
        LOGGER.info("Found {} messages for userId: {}", messages.size(), query.userId());
        
        if (messages.isEmpty()) {
            // Try to find messages as recipient only
            List<Message> recipientMessages = messageRepository.findByRecipientId(query.userId());
            LOGGER.info("Found {} messages as recipient for userId: {}", recipientMessages.size(), query.userId());
            
            // Try to find messages as sender only
            List<Message> senderMessages = messageRepository.findBySenderId(query.userId());
            LOGGER.info("Found {} messages as sender for userId: {}", senderMessages.size(), query.userId());
        } else {
            // Log first few messages for debugging
            messages.stream().limit(3).forEach(msg -> {
                LOGGER.debug("Message ID: {}, Sender: {}, Recipient: {}, Content: {}", 
                    msg.getId(), msg.getSenderId(), msg.getRecipientId(), 
                    msg.getContent().length() > 50 ? msg.getContent().substring(0, 50) + "..." : msg.getContent());
            });
        }
        
        return messages;
    }
}

