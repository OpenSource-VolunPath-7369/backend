package com.acme.center.volunpath_backend.iam.interfaces.rest.transform;

import com.acme.center.volunpath_backend.iam.domain.model.aggregates.User;
import com.acme.center.volunpath_backend.iam.interfaces.rest.resources.AuthenticatedUserResource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticated User Resource From Entity Assembler
 * Transforms User entity and token to AuthenticatedUserResource
 */
public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        List<String> roles = user.getRoles().stream()
            .map(role -> role.getName().name())
            .collect(Collectors.toList());
        
        return new AuthenticatedUserResource(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getName(),
            user.getAvatar(),
            token,
            roles
        );
    }
}

