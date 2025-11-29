package com.acme.center.volunpath_backend.iam.infrastructure.persistence.jpa.repositories;

import com.acme.center.volunpath_backend.iam.domain.model.aggregates.User;
import com.acme.center.volunpath_backend.iam.domain.model.entities.Role;
import com.acme.center.volunpath_backend.iam.domain.model.valueobjects.Roles;
import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;
import com.acme.center.volunpath_backend.organizations.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.acme.center.volunpath_backend.volunteers.domain.model.aggregates.Volunteer;
import com.acme.center.volunpath_backend.volunteers.infrastructure.persistence.jpa.repositories.VolunteerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Synchronizer
 * Synchronizes User records with Volunteer and Organization records
 * Runs on application startup to ensure data consistency
 */
@Component
@Order(2) // Run after DataInitializer (Order 1)
public class DataSynchronizer implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSynchronizer.class);
    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final OrganizationRepository organizationRepository;

    public DataSynchronizer(
            UserRepository userRepository,
            VolunteerRepository volunteerRepository,
            OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        LOGGER.info("Starting data synchronization...");
        synchronizeVolunteers();
        synchronizeOrganizations();
        LOGGER.info("Data synchronization completed");
    }

    private void synchronizeVolunteers() {
        LOGGER.info("Synchronizing Volunteers...");
        
        // Get all users with VOLUNTEER role
        List<User> users = userRepository.findAll();
        int created = 0;
        int updated = 0;
        
        for (User user : users) {
            boolean hasVolunteerRole = user.getRoles().stream()
                    .map(Role::getName)
                    .anyMatch(role -> role.equals(Roles.ROLE_VOLUNTEER));
            
            if (hasVolunteerRole) {
                // Check if volunteer exists by userId
                var existingVolunteer = volunteerRepository.findByUserId(user.getId());
                
                if (existingVolunteer.isEmpty()) {
                    // Check if volunteer exists by email
                    var volunteerByEmail = volunteerRepository.findByEmail(user.getEmail());
                    
                    if (volunteerByEmail.isEmpty()) {
                        // Create new volunteer
                        var volunteer = new Volunteer(
                                user.getName(),
                                user.getEmail(),
                                user.getAvatar(),
                                "",
                                "",
                                user.getId()
                        );
                        volunteerRepository.save(volunteer);
                        created++;
                        LOGGER.info("✅ Created Volunteer record for user ID: {}, email: {}", 
                            user.getId(), user.getEmail());
                    } else {
                        // Update existing volunteer with userId
                        var existing = volunteerByEmail.get();
                        if (existing.getUserId() == null) {
                            existing.setUserId(user.getId());
                            volunteerRepository.save(existing);
                            updated++;
                            LOGGER.info("✅ Linked existing Volunteer to user ID: {}, email: {}", 
                                user.getId(), user.getEmail());
                        }
                    }
                } else {
                    // Ensure userId is set
                    var volunteer = existingVolunteer.get();
                    if (volunteer.getUserId() == null || !volunteer.getUserId().equals(user.getId())) {
                        volunteer.setUserId(user.getId());
                        volunteerRepository.save(volunteer);
                        updated++;
                        LOGGER.info("✅ Updated Volunteer userId for user ID: {}, email: {}", 
                            user.getId(), user.getEmail());
                    }
                }
            }
        }
        
        LOGGER.info("Volunteer synchronization completed: {} created, {} updated", created, updated);
    }

    private void synchronizeOrganizations() {
        LOGGER.info("Synchronizing Organizations...");
        
        // Get all users with ORGANIZATION_ADMIN role
        List<User> users = userRepository.findAll();
        int created = 0;
        int updated = 0;
        
        for (User user : users) {
            boolean hasOrganizationRole = user.getRoles().stream()
                    .map(Role::getName)
                    .anyMatch(role -> role.equals(Roles.ROLE_ORGANIZATION_ADMIN));
            
            if (hasOrganizationRole) {
                // Check if organization exists by userId
                var existingOrganization = organizationRepository.findByUserId(user.getId());
                
                if (existingOrganization.isEmpty()) {
                    // Check if organization exists by email
                    var organizationByEmail = organizationRepository.findByEmail(user.getEmail());
                    
                    if (organizationByEmail.isEmpty()) {
                        // Create new organization
                        var organization = new Organization(
                                user.getName(), // Use user name as organization name initially
                                user.getEmail(),
                                user.getAvatar(),
                                "",
                                "",
                                user.getEmail(), // phone
                                "", // address
                                java.time.Year.now().getValue(), // foundedYear
                                user.getId()
                        );
                        organizationRepository.save(organization);
                        created++;
                        LOGGER.info("✅ Created Organization record for user ID: {}, email: {}", 
                            user.getId(), user.getEmail());
                    } else {
                        // Update existing organization with userId
                        var existing = organizationByEmail.get();
                        if (existing.getUserId() == null) {
                            existing.setUserId(user.getId());
                            organizationRepository.save(existing);
                            updated++;
                            LOGGER.info("✅ Linked existing Organization to user ID: {}, email: {}", 
                                user.getId(), user.getEmail());
                        }
                    }
                } else {
                    // Ensure userId is set
                    var organization = existingOrganization.get();
                    if (organization.getUserId() == null || !organization.getUserId().equals(user.getId())) {
                        organization.setUserId(user.getId());
                        organizationRepository.save(organization);
                        updated++;
                        LOGGER.info("✅ Updated Organization userId for user ID: {}, email: {}", 
                            user.getId(), user.getEmail());
                    }
                }
            }
        }
        
        LOGGER.info("Organization synchronization completed: {} created, {} updated", created, updated);
    }
}

