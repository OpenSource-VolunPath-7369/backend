package com.acme.center.volunpath_backend.organizations.application.internal.commandservices;

import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;
import com.acme.center.volunpath_backend.organizations.domain.model.commands.CreateOrganizationCommand;
import com.acme.center.volunpath_backend.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.acme.center.volunpath_backend.organizations.domain.services.OrganizationCommandService;
import com.acme.center.volunpath_backend.organizations.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Organization Command Service Implementation
 */
@Service
public class OrganizationCommandServiceImpl implements OrganizationCommandService {
    private final OrganizationRepository organizationRepository;

    public OrganizationCommandServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Optional<Organization> handle(CreateOrganizationCommand command) {
        if (organizationRepository.existsByEmail(command.email())) {
            throw new RuntimeException("Organization with this email already exists");
        }
        if (command.userId() != null && organizationRepository.existsByUserId(command.userId())) {
            throw new RuntimeException("Organization with this user ID already exists");
        }

        var organization = new Organization(
                command.name(),
                command.email(),
                command.logo(),
                command.description(),
                command.website(),
                command.phone(),
                command.address(),
                command.foundedYear(),
                command.userId()
        );

        if (command.categories() != null) {
            organization.addCategories(command.categories());
        }

        if (command.socialMedia() != null) {
            organization.setSocialMedia(command.socialMedia());
        }

        organizationRepository.save(organization);
        return Optional.of(organization);
    }

    @Override
    public Optional<Organization> handle(UpdateOrganizationCommand command) {
        var organization = organizationRepository.findById(command.organizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        if (command.name() != null) organization.setName(command.name());
        if (command.email() != null) organization.setEmail(command.email());
        if (command.logo() != null) organization.setLogo(command.logo());
        if (command.description() != null) organization.setDescription(command.description());
        if (command.website() != null) organization.setWebsite(command.website());
        if (command.phone() != null) organization.setPhone(command.phone());
        if (command.address() != null) organization.setAddress(command.address());
        if (command.foundedYear() != null) organization.setFoundedYear(command.foundedYear());
        if (command.categories() != null) {
            organization.getCategories().clear();
            organization.addCategories(command.categories());
        }
        if (command.socialMedia() != null) {
            organization.setSocialMedia(command.socialMedia());
        }
        if (command.isVerified() != null) {
            organization.setIsVerified(command.isVerified());
        }

        organizationRepository.save(organization);
        return Optional.of(organization);
    }

    @Override
    public void handle(Long organizationId) {
        if (!organizationRepository.existsById(organizationId)) {
            throw new RuntimeException("Organization not found");
        }
        organizationRepository.deleteById(organizationId);
    }
}

