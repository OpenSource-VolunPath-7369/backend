package com.acme.center.volunpath_backend.organizations.interfaces.rest.resources;

import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;

import java.util.List;

/**
 * Organization Resource
 * DTO for organization information
 */
public record OrganizationResource(
    Long id,
    String name,
    String logo,
    String description,
    String website,
    String email,
    String phone,
    String address,
    Integer foundedYear,
    Integer volunteerCount,
    Double rating,
    List<String> categories,
    Boolean isVerified,
    Organization.SocialMedia socialMedia,
    Long userId
) {
}

