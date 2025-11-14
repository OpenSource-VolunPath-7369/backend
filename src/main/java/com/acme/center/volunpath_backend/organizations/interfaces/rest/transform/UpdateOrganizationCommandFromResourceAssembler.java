package com.acme.center.volunpath_backend.organizations.interfaces.rest.transform;

import com.acme.center.volunpath_backend.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.acme.center.volunpath_backend.organizations.interfaces.rest.resources.UpdateOrganizationResource;

/**
 * Update Organization Command From Resource Assembler
 */
public class UpdateOrganizationCommandFromResourceAssembler {
    public static UpdateOrganizationCommand toCommandFromResource(Long organizationId, UpdateOrganizationResource resource) {
        return new UpdateOrganizationCommand(
                organizationId,
                resource.name(),
                resource.email(),
                resource.logo(),
                resource.description(),
                resource.website(),
                resource.phone(),
                resource.address(),
                resource.foundedYear(),
                resource.categories(),
                resource.socialMedia(),
                resource.isVerified()
        );
    }
}

