package com.acme.center.volunpath_backend.iam.interfaces.rest.transform;

import com.acme.center.volunpath_backend.iam.domain.model.aggregates.User;
import com.acme.center.volunpath_backend.iam.domain.model.entities.Role;
import com.acme.center.volunpath_backend.iam.interfaces.rest.resources.UserResource;

/**
 * User Resource From Entity Assembler
 * Transforms User entity to UserResource
 */
public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream()
                .map(Role::getStringName)
                .toList();
        
        return new UserResource(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getName(),
            user.getAvatar(),
            roles
        );
    }
}

