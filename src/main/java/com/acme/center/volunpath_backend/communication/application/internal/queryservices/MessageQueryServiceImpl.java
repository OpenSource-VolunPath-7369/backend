package com.acme.center.volunpath_backend.communication.application.internal.queryservices;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetMessageByIdQuery;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetMessagesByUserIdQuery;
import com.acme.center.volunpath_backend.communication.domain.services.MessageQueryService;
import com.acme.center.volunpath_backend.communication.infrastructure.persistence.jpa.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Message Query Service Implementation
 */
@Service
public class MessageQueryServiceImpl implements MessageQueryService {
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
        // Get messages where user is recipient or sender
        return messageRepository.findByRecipientIdOrSenderId(query.userId(), query.userId());
    }
}

