package com.acme.center.volunpath_backend.iam.interfaces.rest.transform;

import com.acme.center.volunpath_backend.iam.domain.model.commands.SignUpCommand;
import com.acme.center.volunpath_backend.iam.domain.model.entities.Role;
import com.acme.center.volunpath_backend.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Sign Up Command From Resource Assembler
 * Transforms SignUpResource to SignUpCommand
 */
public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        List<Role> roles = resource.roles() != null 
            ? resource.roles().stream()
                .map(Role::toRoleFromName)
                .toList()
            : new ArrayList<>();
        
        return new SignUpCommand(
            resource.username(),
            resource.email(),
            resource.password(),
            resource.name(),
            resource.avatar(),
            roles
        );
    }
}

