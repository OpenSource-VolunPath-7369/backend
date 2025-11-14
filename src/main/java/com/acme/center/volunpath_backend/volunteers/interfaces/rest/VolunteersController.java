package com.acme.center.volunpath_backend.volunteers.interfaces.rest;

import com.acme.center.volunpath_backend.volunteers.domain.model.queries.GetAllVolunteersQuery;
import com.acme.center.volunpath_backend.volunteers.domain.model.queries.GetVolunteerByIdQuery;
import com.acme.center.volunpath_backend.volunteers.domain.model.queries.GetVolunteerByUserIdQuery;
import com.acme.center.volunpath_backend.volunteers.domain.services.VolunteerCommandService;
import com.acme.center.volunpath_backend.volunteers.domain.services.VolunteerQueryService;
import com.acme.center.volunpath_backend.volunteers.interfaces.rest.resources.CreateVolunteerResource;
import com.acme.center.volunpath_backend.volunteers.interfaces.rest.resources.UpdateVolunteerResource;
import com.acme.center.volunpath_backend.volunteers.interfaces.rest.resources.VolunteerResource;
import com.acme.center.volunpath_backend.volunteers.interfaces.rest.transform.CreateVolunteerCommandFromResourceAssembler;
import com.acme.center.volunpath_backend.volunteers.interfaces.rest.transform.UpdateVolunteerCommandFromResourceAssembler;
import com.acme.center.volunpath_backend.volunteers.interfaces.rest.transform.VolunteerResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Volunteers Controller
 */
@RestController
@RequestMapping(value = "/api/v1/volunteers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Volunteers", description = "Volunteer Management Endpoints")
public class VolunteersController {
    private final VolunteerCommandService volunteerCommandService;
    private final VolunteerQueryService volunteerQueryService;

    public VolunteersController(
            VolunteerCommandService volunteerCommandService,
            VolunteerQueryService volunteerQueryService) {
        this.volunteerCommandService = volunteerCommandService;
        this.volunteerQueryService = volunteerQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all volunteers")
    public ResponseEntity<List<VolunteerResource>> getAllVolunteers() {
        var volunteers = volunteerQueryService.handle(new GetAllVolunteersQuery());
        var resources = volunteers.stream()
                .map(VolunteerResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get volunteer by ID")
    public ResponseEntity<VolunteerResource> getVolunteerById(@PathVariable Long id) {
        var volunteer = volunteerQueryService.handle(new GetVolunteerByIdQuery(id));
        if (volunteer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = VolunteerResourceFromEntityAssembler.toResourceFromEntity(volunteer.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get volunteer by user ID")
    public ResponseEntity<VolunteerResource> getVolunteerByUserId(@PathVariable Long userId) {
        var volunteer = volunteerQueryService.handle(new GetVolunteerByUserIdQuery(userId));
        if (volunteer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = VolunteerResourceFromEntityAssembler.toResourceFromEntity(volunteer.get());
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    @Operation(summary = "Create a new volunteer")
    public ResponseEntity<VolunteerResource> createVolunteer(@RequestBody CreateVolunteerResource resource) {
        var command = CreateVolunteerCommandFromResourceAssembler.toCommandFromResource(resource);
        var volunteer = volunteerCommandService.handle(command);
        if (volunteer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var volunteerResource = VolunteerResourceFromEntityAssembler.toResourceFromEntity(volunteer.get());
        return new ResponseEntity<>(volunteerResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a volunteer")
    public ResponseEntity<VolunteerResource> updateVolunteer(@PathVariable Long id, @RequestBody UpdateVolunteerResource resource) {
        var command = UpdateVolunteerCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var volunteer = volunteerCommandService.handle(command);
        if (volunteer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var volunteerResource = VolunteerResourceFromEntityAssembler.toResourceFromEntity(volunteer.get());
        return ResponseEntity.ok(volunteerResource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a volunteer")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        try {
            volunteerCommandService.handle(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

