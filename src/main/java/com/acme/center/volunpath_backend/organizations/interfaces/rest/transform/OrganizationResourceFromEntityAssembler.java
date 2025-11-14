package com.acme.center.volunpath_backend.organizations.interfaces.rest.transform;

import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;
import com.acme.center.volunpath_backend.organizations.interfaces.rest.resources.OrganizationResource;

/**
 * Organization Resource From Entity Assembler
 */
public class OrganizationResourceFromEntityAssembler {
    public static OrganizationResource toResourceFromEntity(Organization organization) {
        return new OrganizationResource(
                organization.getId(),
                organization.getName(),
                organization.getLogo(),
                organization.getDescription(),
                organization.getWebsite(),
                organization.getEmail(),
                organization.getPhone(),
                organization.getAddress(),
                organization.getFoundedYear(),
                organization.getVolunteerCount(),
                organization.getRating(),
                organization.getCategories(),
                organization.getIsVerified(),
                organization.getSocialMedia(),
                organization.getUserId()
        );
    }
}

