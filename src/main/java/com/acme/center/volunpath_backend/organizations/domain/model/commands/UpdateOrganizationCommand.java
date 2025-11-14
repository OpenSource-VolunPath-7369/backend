package com.acme.center.volunpath_backend.organizations.domain.model.commands;

import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;

import java.util.List;

/**
 * Update Organization Command
 */
public record UpdateOrganizationCommand(
    Long organizationId,
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

