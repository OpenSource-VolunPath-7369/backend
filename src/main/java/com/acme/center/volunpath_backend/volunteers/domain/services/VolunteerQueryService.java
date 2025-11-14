package com.acme.center.volunpath_backend.volunteers.domain.services;

import com.acme.center.volunpath_backend.volunteers.domain.model.aggregates.Volunteer;
import com.acme.center.volunpath_backend.volunteers.domain.model.queries.GetAllVolunteersQuery;
import com.acme.center.volunpath_backend.volunteers.domain.model.queries.GetVolunteerByIdQuery;
import com.acme.center.volunpath_backend.volunteers.domain.model.queries.GetVolunteerByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Volunteer Query Service
 */
public interface VolunteerQueryService {
    List<Volunteer> handle(GetAllVolunteersQuery query);
    Optional<Volunteer> handle(GetVolunteerByIdQuery query);
    Optional<Volunteer> handle(GetVolunteerByUserIdQuery query);
}

