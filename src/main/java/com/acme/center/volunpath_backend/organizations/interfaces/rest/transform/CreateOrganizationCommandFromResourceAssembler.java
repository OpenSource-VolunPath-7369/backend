package com.acme.center.volunpath_backend.organizations.interfaces.rest.transform;

import com.acme.center.volunpath_backend.organizations.domain.model.commands.CreateOrganizationCommand;
import com.acme.center.volunpath_backend.organizations.interfaces.rest.resources.CreateOrganizationResource;

/**
 * Create Organization Command From Resource Assembler
 */
public class CreateOrganizationCommandFromResourceAssembler {
    public static CreateOrganizationCommand toCommandFromResource(CreateOrganizationResource resource) {
        return new CreateOrganizationCommand(
                resource.name(),
                resource.email(),
                resource.logo(),
                resource.description(),
                resource.website(),
                resource.phone(),
                resource.address(),
                resource.foundedYear(),
                resource.userId(),
                resource.categories(),
                resource.socialMedia()
        );
    }
}

