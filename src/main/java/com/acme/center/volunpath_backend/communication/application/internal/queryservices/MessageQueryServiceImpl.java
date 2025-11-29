package com.acme.center.volunpath_backend.communication.application.internal.queryservices;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Message;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetMessageByIdQuery;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetMessagesByUserIdQuery;
import com.acme.center.volunpath_backend.communication.domain.services.MessageQueryService;
import com.acme.center.volunpath_backend.communication.infrastructure.persistence.jpa.repositories.MessageRepository;
import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;
import com.acme.center.volunpath_backend.organizations.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.acme.center.volunpath_backend.volunteers.domain.model.aggregates.Volunteer;
import com.acme.center.volunpath_backend.volunteers.infrastructure.persistence.jpa.repositories.VolunteerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Message Query Service Implementation
 */
@Service
public class MessageQueryServiceImpl implements MessageQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageQueryServiceImpl.class);
    private final MessageRepository messageRepository;
    private final VolunteerRepository volunteerRepository;
    private final OrganizationRepository organizationRepository;

    public MessageQueryServiceImpl(
            MessageRepository messageRepository,
            VolunteerRepository volunteerRepository,
            OrganizationRepository organizationRepository) {
        this.messageRepository = messageRepository;
        this.volunteerRepository = volunteerRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Optional<Message> handle(GetMessageByIdQuery query) {
        return messageRepository.findById(query.messageId());
    }

    @Override
    public List<Message> handle(GetMessagesByUserIdQuery query) {
        LOGGER.info("Querying messages for userId: {}", query.userId());
        
        List<Long> idsToSearch = new ArrayList<>();
        idsToSearch.add(query.userId());
        
        // Check if this userId corresponds to a volunteer
        // If so, we need to search by both userId and volunteer.id
        Optional<Volunteer> volunteer = volunteerRepository.findByUserId(query.userId());
        if (volunteer.isPresent()) {
            Long volunteerId = volunteer.get().getId();
            LOGGER.info("User {} is a volunteer with volunteer.id: {}", query.userId(), volunteerId);
            // Only add volunteerId if it's different from userId
            if (!volunteerId.equals(query.userId())) {
                idsToSearch.add(volunteerId);
                LOGGER.info("Will search messages for both userId: {} and volunteerId: {}", query.userId(), volunteerId);
            }
        }
        
        // Check if this userId corresponds to an organization
        // If so, we need to search by both userId and organization.id
        Optional<Organization> organization = organizationRepository.findByUserId(query.userId());
        if (organization.isPresent()) {
            Long organizationId = organization.get().getId();
            LOGGER.info("User {} is an organization with organization.id: {}", query.userId(), organizationId);
            // Only add organizationId if it's different from userId
            if (!organizationId.equals(query.userId())) {
                idsToSearch.add(organizationId);
                LOGGER.info("Will search messages for both userId: {} and organizationId: {}", query.userId(), organizationId);
            }
        }
        
        // Search messages for all relevant IDs
        Set<Long> seenIds = new HashSet<>();
        List<Message> combinedMessages = new ArrayList<>();
        
        for (Long id : idsToSearch) {
            // Messages where user is recipient
            List<Message> recipientMessages = messageRepository.findByRecipientId(id);
            LOGGER.info("Found {} messages as recipient for id: {}", recipientMessages.size(), id);
            
            // Messages where user is sender
            List<Message> senderMessages = messageRepository.findBySenderId(id);
            LOGGER.info("Found {} messages as sender for id: {}", senderMessages.size(), id);
            
            // Add recipient messages (avoiding duplicates)
            for (Message msg : recipientMessages) {
                if (!seenIds.contains(msg.getId())) {
                    combinedMessages.add(msg);
                    seenIds.add(msg.getId());
                }
            }
            
            // Add sender messages (avoiding duplicates)
            for (Message msg : senderMessages) {
                if (!seenIds.contains(msg.getId())) {
                    combinedMessages.add(msg);
                    seenIds.add(msg.getId());
                }
            }
        }
        
        LOGGER.info("Combined total: {} unique messages for userId: {} (searched {} IDs)", 
            combinedMessages.size(), query.userId(), idsToSearch.size());
        
        // Log first few messages for debugging
        if (!combinedMessages.isEmpty()) {
            combinedMessages.stream().limit(5).forEach(msg -> {
                LOGGER.info("Message ID: {}, SenderId: {}, RecipientId: {}, SenderName: {}, Content preview: {}", 
                    msg.getId(), msg.getSenderId(), msg.getRecipientId(), msg.getSenderName(),
                    msg.getContent().length() > 50 ? msg.getContent().substring(0, 50) + "..." : msg.getContent());
            });
        } else {
            LOGGER.warn("No messages found for userId: {} (searched IDs: {})", query.userId(), idsToSearch);
        }
        
        return combinedMessages;
    }
}

