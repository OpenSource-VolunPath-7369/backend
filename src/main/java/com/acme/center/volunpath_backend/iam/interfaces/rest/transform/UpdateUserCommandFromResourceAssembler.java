package com.acme.center.volunpath_backend.iam.interfaces.rest.transform;

import com.acme.center.volunpath_backend.iam.domain.model.commands.UpdateUserCommand;
import com.acme.center.volunpath_backend.iam.interfaces.rest.resources.UpdateUserResource;

/**
 * Update User Command From Resource Assembler
 */
public class UpdateUserCommandFromResourceAssembler {
    public static UpdateUserCommand toCommandFromResource(Long userId, UpdateUserResource resource) {
        return new UpdateUserCommand(
                userId,
                resource.name(),
                resource.email(),
                resource.avatar()
        );
    }
}

