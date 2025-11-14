package com.acme.center.volunpath_backend.iam.domain.model.queries;

import com.acme.center.volunpath_backend.iam.domain.model.valueobjects.Roles;

/**
 * Get role by name query
 */
public record GetRoleByNameQuery(Roles roleName) {
}

