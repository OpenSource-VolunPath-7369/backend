package com.acme.center.volunpath_backend.iam.domain.services;

import com.acme.center.volunpath_backend.iam.domain.model.aggregates.User;
import com.acme.center.volunpath_backend.iam.domain.model.queries.GetAllUsersQuery;
import com.acme.center.volunpath_backend.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.center.volunpath_backend.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

/**
 * User Query Service
 * Handles user queries
 */
public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
}

