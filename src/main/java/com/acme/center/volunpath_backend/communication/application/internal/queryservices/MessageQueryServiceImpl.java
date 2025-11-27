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
        
        // Try separate queries first to debug
        List<Message> recipientMessages = messageRepository.findByRecipientId(query.userId());
        LOGGER.info("Found {} messages as recipient for userId: {}", recipientMessages.size(), query.userId());
        
        List<Message> senderMessages = messageRepository.findBySenderId(query.userId());
        LOGGER.info("Found {} messages as sender for userId: {}", senderMessages.size(), query.userId());
        
        // Combine both lists and remove duplicates
        java.util.Set<Long> seenIds = new java.util.HashSet<>();
        List<Message> combinedMessages = new java.util.ArrayList<>();
        
        for (Message msg : recipientMessages) {
            if (!seenIds.contains(msg.getId())) {
                combinedMessages.add(msg);
                seenIds.add(msg.getId());
            }
        }
        
        for (Message msg : senderMessages) {
            if (!seenIds.contains(msg.getId())) {
                combinedMessages.add(msg);
                seenIds.add(msg.getId());
            }
        }
        
        LOGGER.info("Combined total: {} unique messages for userId: {}", combinedMessages.size(), query.userId());
        
        // Also try the original method for comparison
        List<Message> orMethodMessages = messageRepository.findByRecipientIdOrSenderId(query.userId(), query.userId());
        LOGGER.info("findByRecipientIdOrSenderId returned {} messages", orMethodMessages.size());
        
        // Log first few messages for debugging
        if (!combinedMessages.isEmpty()) {
            combinedMessages.stream().limit(3).forEach(msg -> {
                LOGGER.info("Message ID: {}, Sender: {}, Recipient: {}, Content: {}", 
                    msg.getId(), msg.getSenderId(), msg.getRecipientId(), 
                    msg.getContent().length() > 50 ? msg.getContent().substring(0, 50) + "..." : msg.getContent());
            });
        }
        
        // Return combined messages (more reliable)
        return combinedMessages;
    }
}

