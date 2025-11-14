package com.acme.center.volunpath_backend.organizations.application.internal.queryservices;

import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;
import com.acme.center.volunpath_backend.organizations.domain.model.queries.GetAllOrganizationsQuery;
import com.acme.center.volunpath_backend.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.acme.center.volunpath_backend.organizations.domain.model.queries.GetOrganizationByUserIdQuery;
import com.acme.center.volunpath_backend.organizations.domain.services.OrganizationQueryService;
import com.acme.center.volunpath_backend.organizations.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Organization Query Service Implementation
 */
@Service
public class OrganizationQueryServiceImpl implements OrganizationQueryService {
    private final OrganizationRepository organizationRepository;

    public OrganizationQueryServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public List<Organization> handle(GetAllOrganizationsQuery query) {
        return organizationRepository.findAll();
    }

    @Override
    public Optional<Organization> handle(GetOrganizationByIdQuery query) {
        return organizationRepository.findById(query.organizationId());
    }

    @Override
    public Optional<Organization> handle(GetOrganizationByUserIdQuery query) {
        return organizationRepository.findByUserId(query.userId());
    }
}

