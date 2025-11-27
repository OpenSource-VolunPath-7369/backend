package com.acme.center.volunpath_backend.communication.interfaces.rest;

import com.acme.center.volunpath_backend.communication.domain.model.queries.GetMessageByIdQuery;
import com.acme.center.volunpath_backend.communication.domain.model.queries.GetMessagesByUserIdQuery;
import com.acme.center.volunpath_backend.communication.domain.services.MessageCommandService;
import com.acme.center.volunpath_backend.communication.domain.services.MessageQueryService;
import com.acme.center.volunpath_backend.communication.interfaces.rest.resources.CreateMessageResource;
import com.acme.center.volunpath_backend.communication.interfaces.rest.resources.MessageResource;
import com.acme.center.volunpath_backend.communication.interfaces.rest.transform.CreateMessageCommandFromResourceAssembler;
import com.acme.center.volunpath_backend.communication.interfaces.rest.transform.MessageResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Messages Controller
 */
@RestController
@RequestMapping(value = "/api/v1/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Messages", description = "Message Management Endpoints")
public class MessagesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagesController.class);
    private final MessageCommandService messageCommandService;
    private final MessageQueryService messageQueryService;

    public MessagesController(
            MessageCommandService messageCommandService,
            MessageQueryService messageQueryService) {
        this.messageCommandService = messageCommandService;
        this.messageQueryService = messageQueryService;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get messages by user ID")
    public ResponseEntity<List<MessageResource>> getMessagesByUserId(@PathVariable Long userId) {
        LOGGER.info("GET /messages/user/{} - Request received", userId);
        var messages = messageQueryService.handle(new GetMessagesByUserIdQuery(userId));
        LOGGER.info("GET /messages/user/{} - Found {} messages", userId, messages.size());
        var resources = messages.stream()
                .map(MessageResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        LOGGER.info("GET /messages/user/{} - Returning {} message resources", userId, resources.size());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get message by ID")
    public ResponseEntity<MessageResource> getMessageById(@PathVariable Long id) {
        var message = messageQueryService.handle(new GetMessageByIdQuery(id));
        if (message.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = MessageResourceFromEntityAssembler.toResourceFromEntity(message.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/debug/all")
    @Operation(summary = "DEBUG: Get all messages (for debugging only)")
    public ResponseEntity<List<MessageResource>> getAllMessages() {
        LOGGER.warn("DEBUG: Getting all messages - this endpoint should be removed in production");
        // This is a temporary debug endpoint
        var allMessages = messageQueryService.handle(new GetMessagesByUserIdQuery(0L));
        // Actually, we need to get all messages, not by userId
        // For now, let's just return an empty list and log
        LOGGER.info("DEBUG: Total messages in database would be queried here");
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    @Operation(summary = "Create a new message")
    public ResponseEntity<MessageResource> createMessage(@RequestBody CreateMessageResource resource) {
        var command = CreateMessageCommandFromResourceAssembler.toCommandFromResource(resource);
        var message = messageCommandService.handle(command);
        if (message.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var messageResource = MessageResourceFromEntityAssembler.toResourceFromEntity(message.get());
        return new ResponseEntity<>(messageResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark message as read")
    public ResponseEntity<MessageResource> markAsRead(@PathVariable Long id) {
        var message = messageCommandService.handle(id);
        if (message.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = MessageResourceFromEntityAssembler.toResourceFromEntity(message.get());
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a message")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        try {
            messageCommandService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

