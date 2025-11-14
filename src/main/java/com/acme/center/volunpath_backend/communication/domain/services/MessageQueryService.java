package com.acme.center.volunpath_backend.communication.domain.services;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetMessageByIdQuery;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetMessagesByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Message Query Service
 */
public interface MessageQueryService {
    Optional<Message> handle(GetMessageByIdQuery query);
    List<Message> handle(GetMessagesByUserIdQuery query);
}

