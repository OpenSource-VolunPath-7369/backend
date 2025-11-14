package com.acme.center.volunpath_backend.organizations.interfaces.rest.resources;

import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;

import java.util.List;

/**
 * Update Organization Resource
 * DTO for updating an organization
 */
public record UpdateOrganizationResource(
    String name,
    String email,
    String logo,
    String description,
    String website,
    String phone,
    String address,
    Integer foundedYear,
    List<String> categories,
    Organization.SocialMedia socialMedia,
    Boolean isVerified
) {
}

