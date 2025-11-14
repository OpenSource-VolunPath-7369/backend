package com.acme.center.volunpath_backend.communication.domain.model.aggregates;

import com.acme.center.volunpath_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Message aggregate root
 * Represents a message between users in the Volunpath system
 */
@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message extends AuditableAbstractAggregateRoot<Message> {

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Size(max = 200)
    @Column(name = "sender_name")
    private String senderName;

    @Size(max = 100000)
    @Column(name = "sender_icon", columnDefinition = "LONGTEXT")
    private String senderIcon;

    @Column(name = "recipient_id", nullable = false)
    private Long recipientId;

    @NotBlank
    @Size(max = 2000)
    @Column(columnDefinition = "TEXT")
    private String content;

    private Boolean isRead = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private MessageType type = MessageType.GENERAL;

    @Size(max = 200)
    @Column(name = "sender_organization")
    private String senderOrganization;

    public Message() {
        this.isRead = false;
        this.type = MessageType.GENERAL;
    }

    public Message(Long senderId, String senderName, String senderIcon, Long recipientId,
                   String content, MessageType type, String senderOrganization) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderIcon = senderIcon;
        this.recipientId = recipientId;
        this.content = content;
        this.type = type;
        this.senderOrganization = senderOrganization;
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public void markAsUnread() {
        this.isRead = false;
    }

    public boolean isUnread() {
        return !isRead;
    }

    public enum MessageType {
        VOLUNTEER_INQUIRY,
        ACTIVITY_DETAILS,
        CONFIRMATION,
        THANK_YOU,
        GENERAL
    }
}

