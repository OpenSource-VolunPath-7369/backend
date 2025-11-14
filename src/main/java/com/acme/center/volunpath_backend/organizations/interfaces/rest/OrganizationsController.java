package com.acme.center.volunpath_backend.organizations.interfaces.rest;

import com.acme.center.volunpath_backend.organizations.domain.model.queries.GetAllOrganizationsQuery;
import com.acme.center.volunpath_backend.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.acme.center.volunpath_backend.organizations.domain.model.queries.GetOrganizationByUserIdQuery;
import com.acme.center.volunpath_backend.organizations.domain.services.OrganizationCommandService;
import com.acme.center.volunpath_backend.organizations.domain.services.OrganizationQueryService;
import com.acme.center.volunpath_backend.organizations.interfaces.rest.resources.CreateOrganizationResource;
import com.acme.center.volunpath_backend.organizations.interfaces.rest.resources.UpdateOrganizationResource;
import com.acme.center.volunpath_backend.organizations.interfaces.rest.resources.OrganizationResource;
import com.acme.center.volunpath_backend.organizations.interfaces.rest.transform.CreateOrganizationCommandFromResourceAssembler;
import com.acme.center.volunpath_backend.organizations.interfaces.rest.transform.UpdateOrganizationCommandFromResourceAssembler;
import com.acme.center.volunpath_backend.organizations.interfaces.rest.transform.OrganizationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Organizations Controller
 */
@RestController
@RequestMapping(value = "/api/v1/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Organizations", description = "Organization Management Endpoints")
public class OrganizationsController {
    private final OrganizationCommandService organizationCommandService;
    private final OrganizationQueryService organizationQueryService;

    public OrganizationsController(
            OrganizationCommandService organizationCommandService,
            OrganizationQueryService organizationQueryService) {
        this.organizationCommandService = organizationCommandService;
        this.organizationQueryService = organizationQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all organizations")
    public ResponseEntity<List<OrganizationResource>> getAllOrganizations() {
        var organizations = organizationQueryService.handle(new GetAllOrganizationsQuery());
        var resources = organizations.stream()
                .map(OrganizationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get organization by ID")
    public ResponseEntity<OrganizationResource> getOrganizationById(@PathVariable Long id) {
        var organization = organizationQueryService.handle(new GetOrganizationByIdQuery(id));
        if (organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = OrganizationResourceFromEntityAssembler.toResourceFromEntity(organization.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get organization by user ID")
    public ResponseEntity<OrganizationResource> getOrganizationByUserId(@PathVariable Long userId) {
        var organization = organizationQueryService.handle(new GetOrganizationByUserIdQuery(userId));
        if (organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = OrganizationResourceFromEntityAssembler.toResourceFromEntity(organization.get());
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    @Operation(summary = "Create a new organization")
    public ResponseEntity<OrganizationResource> createOrganization(@RequestBody CreateOrganizationResource resource) {
        var command = CreateOrganizationCommandFromResourceAssembler.toCommandFromResource(resource);
        var organization = organizationCommandService.handle(command);
        if (organization.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var organizationResource = OrganizationResourceFromEntityAssembler.toResourceFromEntity(organization.get());
        return new ResponseEntity<>(organizationResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an organization")
    public ResponseEntity<OrganizationResource> updateOrganization(@PathVariable Long id, @RequestBody UpdateOrganizationResource resource) {
        var command = UpdateOrganizationCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var organization = organizationCommandService.handle(command);
        if (organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var organizationResource = OrganizationResourceFromEntityAssembler.toResourceFromEntity(organization.get());
        return ResponseEntity.ok(organizationResource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an organization")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        try {
            organizationCommandService.handle(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

