package com.acme.center.volunpath_backend.volunteers.interfaces.rest.transform;

import com.acme.center.volunpath_backend.volunteers.domain.model.aggregates.Volunteer;
import com.acme.center.volunpath_backend.volunteers.interfaces.rest.resources.VolunteerResource;

/**
 * Volunteer Resource From Entity Assembler
 */
public class VolunteerResourceFromEntityAssembler {
    public static VolunteerResource toResourceFromEntity(Volunteer volunteer) {
        return new VolunteerResource(
                volunteer.getId(),
                volunteer.getName(),
                volunteer.getEmail(),
                volunteer.getAvatar(),
                volunteer.getBio(),
                volunteer.getLocation(),
                volunteer.getUserId(),
                volunteer.getSkills()
        );
    }
}

