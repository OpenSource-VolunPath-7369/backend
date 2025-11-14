package com.acme.center.volunpath_backend.communication.interfaces.rest;

import com.acme.center.volunpath_backend.communication.domain.model.queries.GetNotificationByIdQuery;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetNotificationsByUserIdQuery;
import com.acme.center.volunpath_backend.communication.domain.services.NotificationCommandService;
import com.acme.center.volunpath_backend.communication.domain.services.NotificationQueryService;
import com.acme.center.volunpath_backend.communication.interfaces.rest.resources.CreateNotificationResource;
import com.acme.center.volunpath_backend.communication.interfaces.rest.resources.NotificationResource;
import com.acme.center.volunpath_backend.communication.interfaces.rest.transform.CreateNotificationCommandFromResourceAssembler;
import com.acme.center.volunpath_backend.communication.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Notifications Controller
 */
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Notification Management Endpoints")
public class NotificationsController {
    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;

    public NotificationsController(
            NotificationCommandService notificationCommandService,
            NotificationQueryService notificationQueryService) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get notifications by user ID")
    public ResponseEntity<List<NotificationResource>> getNotificationsByUserId(@PathVariable Long userId) {
        var notifications = notificationQueryService.handle(new GetNotificationsByUserIdQuery(userId));
        var resources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID")
    public ResponseEntity<NotificationResource> getNotificationById(@PathVariable Long id) {
        var notification = notificationQueryService.handle(new GetNotificationByIdQuery(id));
        if (notification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    @Operation(summary = "Create a new notification")
    public ResponseEntity<NotificationResource> createNotification(@RequestBody CreateNotificationResource resource) {
        var command = CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource);
        var notification = notificationCommandService.handle(command);
        if (notification.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
        return new ResponseEntity<>(notificationResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark notification as read")
    public ResponseEntity<NotificationResource> markAsRead(@PathVariable Long id) {
        var notification = notificationCommandService.handle(id);
        if (notification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/user/{userId}/read-all")
    @Operation(summary = "Mark all notifications as read for a user")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Long userId) {
        notificationCommandService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        try {
            notificationCommandService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

