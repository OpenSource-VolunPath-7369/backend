package com.acme.center.volunpath_backend.iam.domain.services;

import com.acme.center.volunpath_backend.iam.domain.model.aggregates.User;
import com.acme.center.volunpath_backend.iam.domain.model.commands.SignInCommand;
import com.acme.center.volunpath_backend.iam.domain.model.commands.SignUpCommand;
import com.acme.center.volunpath_backend.iam.domain.model.commands.UpdateUserCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

/**
 * User Command Service
 * Handles user commands (sign-in, sign-up, update)
 */
public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);
    Optional<User> handle(UpdateUserCommand command);
}

