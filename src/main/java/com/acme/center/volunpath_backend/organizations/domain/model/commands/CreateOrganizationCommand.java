package com.acme.center.volunpath_backend.organizations.domain.model.commands;

import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;

import java.util.List;

/**
 * Create Organization Command
 */
public record CreateOrganizationCommand(
    String name,
    String email,
    String logo,
    String description,
    String website,
    String phone,
    String address,
    Integer foundedYear,
    Long userId,
    List<String> categories,
    Organization.SocialMedia socialMedia
) {
}

