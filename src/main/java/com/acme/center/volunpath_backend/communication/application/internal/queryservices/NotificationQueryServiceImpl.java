package com.acme.center.volunpath_backend.communication.application.internal.queryservices;

import com.acme.center.volunpath_backend.communication.domain.model.aggregates.Notification;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetNotificationByIdQuery;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetNotificationsByUserIdQuery;
import com.acme.center.volunpath_backend.communication.domain.services.NotificationQueryService;
import com.acme.center.volunpath_backend.communication.infrastructure.persistence.jpa.repositories.NotificationRepository;
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
 * Notification Query Service Implementation
 */
@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationQueryServiceImpl.class);
    private final NotificationRepository notificationRepository;
    private final VolunteerRepository volunteerRepository;

    public NotificationQueryServiceImpl(
            NotificationRepository notificationRepository,
            VolunteerRepository volunteerRepository) {
        this.notificationRepository = notificationRepository;
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        return notificationRepository.findById(query.notificationId());
    }

    @Override
    public List<Notification> handle(GetNotificationsByUserIdQuery query) {
        LOGGER.info("Querying notifications for userId: {}", query.userId());
        
        // Check if this userId corresponds to a volunteer
        // If so, we need to search by both userId and volunteer.id
        Optional<Volunteer> volunteer = volunteerRepository.findByUserId(query.userId());
        List<Long> idsToSearch = new ArrayList<>();
        idsToSearch.add(query.userId());
        
        if (volunteer.isPresent()) {
            Long volunteerId = volunteer.get().getId();
            LOGGER.info("User {} is a volunteer with volunteer.id: {}", query.userId(), volunteerId);
            // Only add volunteerId if it's different from userId
            if (!volunteerId.equals(query.userId())) {
                idsToSearch.add(volunteerId);
                LOGGER.info("Will search notifications for both userId: {} and volunteerId: {}", query.userId(), volunteerId);
            }
        }
        
        // Search notifications for all relevant IDs
        Set<Long> seenIds = new HashSet<>();
        List<Notification> combinedNotifications = new ArrayList<>();
        
        for (Long id : idsToSearch) {
            List<Notification> notifications = notificationRepository.findByUserId(id);
            LOGGER.info("Found {} notifications for id: {}", notifications.size(), id);
            
            // Add notifications (avoiding duplicates)
            for (Notification notification : notifications) {
                if (!seenIds.contains(notification.getId())) {
                    combinedNotifications.add(notification);
                    seenIds.add(notification.getId());
                }
            }
        }
        
        LOGGER.info("Combined total: {} unique notifications for userId: {} (searched {} IDs)", 
            combinedNotifications.size(), query.userId(), idsToSearch.size());
        
        if (combinedNotifications.isEmpty()) {
            LOGGER.warn("No notifications found for userId: {} (searched IDs: {})", query.userId(), idsToSearch);
        }
        
        return combinedNotifications;
    }
}

