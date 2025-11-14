package com.acme.center.volunpath_backend.organizations.domain.services;

import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;
import com.acme.center.volunpath_backend.organizations.domain.model.commands.CreateOrganizationCommand;
import com.acme.center.volunpath_backend.organizations.domain.model.commands.UpdateOrganizationCommand;

import java.util.Optional;

/**
 * Organization Command Service
 */
public interface OrganizationCommandService {
    Optional<Organization> handle(CreateOrganizationCommand command);
    Optional<Organization> handle(UpdateOrganizationCommand command);
    void handle(Long organizationId); // Delete
}

