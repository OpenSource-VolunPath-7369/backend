package com.acme.center.volunpath_backend.iam.interfaces.rest.transform;

import com.acme.center.volunpath_backend.iam.domain.model.commands.SignInCommand;
import com.acme.center.volunpath_backend.iam.interfaces.rest.resources.SignInResource;

/**
 * Sign In Command From Resource Assembler
 * Transforms SignInResource to SignInCommand
 */
public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(
            signInResource.username(),
            signInResource.password()
        );
    }
}

