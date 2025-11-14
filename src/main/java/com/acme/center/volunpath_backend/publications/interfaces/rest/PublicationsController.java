package com.acme.center.volunpath_backend.publications.interfaces.rest;

import com.acme.center.volunpath_backend.publications.domain.model.queries.GetAllPublicationsQuery;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetPublicationByIdQuery;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetPublicationsByOrganizationIdQuery;
import com.acme.center.volunpath_backend.publications.domain.services.PublicationCommandService;
import com.acme.center.volunpath_backend.publications.domain.services.PublicationQueryService;
import com.acme.center.volunpath_backend.publications.interfaces.rest.resources.CreatePublicationResource;
import com.acme.center.volunpath_backend.publications.interfaces.rest.resources.PublicationResource;
import com.acme.center.volunpath_backend.publications.interfaces.rest.transform.CreatePublicationCommandFromResourceAssembler;
import com.acme.center.volunpath_backend.publications.interfaces.rest.transform.PublicationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Publications Controller
 */
@RestController
@RequestMapping(value = "/api/v1/publications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Publications", description = "Publication Management Endpoints")
public class PublicationsController {
    private final PublicationCommandService publicationCommandService;
    private final PublicationQueryService publicationQueryService;

    public PublicationsController(
            PublicationCommandService publicationCommandService,
            PublicationQueryService publicationQueryService) {
        this.publicationCommandService = publicationCommandService;
        this.publicationQueryService = publicationQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all publications")
    public ResponseEntity<List<PublicationResource>> getAllPublications() {
        var publications = publicationQueryService.handle(new GetAllPublicationsQuery());
        var resources = publications.stream()
                .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get publication by ID")
    public ResponseEntity<PublicationResource> getPublicationById(@PathVariable Long id) {
        var publication = publicationQueryService.handle(new GetPublicationByIdQuery(id));
        if (publication.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = PublicationResourceFromEntityAssembler.toResourceFromEntity(publication.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/organization/{organizationId}")
    @Operation(summary = "Get publications by organization ID")
    public ResponseEntity<List<PublicationResource>> getPublicationsByOrganizationId(@PathVariable Long organizationId) {
        var publications = publicationQueryService.handle(new GetPublicationsByOrganizationIdQuery(organizationId));
        var resources = publications.stream()
                .map(PublicationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping
    @Operation(summary = "Create a new publication")
    public ResponseEntity<PublicationResource> createPublication(@RequestBody CreatePublicationResource resource) {
        var command = CreatePublicationCommandFromResourceAssembler.toCommandFromResource(resource);
        var publication = publicationCommandService.handle(command);
        if (publication.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var publicationResource = PublicationResourceFromEntityAssembler.toResourceFromEntity(publication.get());
        return new ResponseEntity<>(publicationResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/like")
    @Operation(summary = "Like a publication")
    public ResponseEntity<PublicationResource> likePublication(@PathVariable Long id) {
        var publication = publicationCommandService.handle(id);
        if (publication.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = PublicationResourceFromEntityAssembler.toResourceFromEntity(publication.get());
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a publication")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        try {
            publicationCommandService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

