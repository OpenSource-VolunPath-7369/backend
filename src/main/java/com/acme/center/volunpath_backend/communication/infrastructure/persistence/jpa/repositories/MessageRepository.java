package com.acme.center.volunpath_backend.communication.infrastructure.persistence.jpa.repositories;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRecipientId(Long recipientId);
    List<Message> findBySenderId(Long senderId);
    List<Message> findByRecipientIdOrSenderId(Long recipientId, Long senderId);
    long countByRecipientIdAndIsReadFalse(Long recipientId);
}

