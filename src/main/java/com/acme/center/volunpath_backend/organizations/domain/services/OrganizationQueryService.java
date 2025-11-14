package com.acme.center.volunpath_backend.organizations.domain.services;

import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;
import com.acme.center.volunpath_backend.organizations.domain.model.queries.GetAllOrganizationsQuery;
import com.acme.center.volunpath_backend.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.acme.center.volunpath_backend.organizations.domain.model.queries.GetOrganizationByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Organization Query Service
 */
public interface OrganizationQueryService {
    List<Organization> handle(GetAllOrganizationsQuery query);
    Optional<Organization> handle(GetOrganizationByIdQuery query);
    Optional<Organization> handle(GetOrganizationByUserIdQuery query);
}

