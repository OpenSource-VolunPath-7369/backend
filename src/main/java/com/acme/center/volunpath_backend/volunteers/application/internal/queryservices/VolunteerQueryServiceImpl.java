package com.acme.center.volunpath_backend.volunteers.application.internal.queryservices;

import com.acme.center.volunpath_backend.volunteers.domain.model.aggregates.Volunteer;
import com.acme.center.volunpath_backend.volunteers.domain.model.queries.GetAllVolunteersQuery;
import com.acme.center.volunpath_backend.volunteers.domain.model.queries.GetVolunteerByIdQuery;
import com.acme.center.volunpath_backend.volunteers.domain.model.queries.GetVolunteerByUserIdQuery;
import com.acme.center.volunpath_backend.volunteers.domain.services.VolunteerQueryService;
import com.acme.center.volunpath_backend.volunteers.infrastructure.persistence.jpa.repositories.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Volunteer Query Service Implementation
 */
@Service
public class VolunteerQueryServiceImpl implements VolunteerQueryService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerQueryServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public List<Volunteer> handle(GetAllVolunteersQuery query) {
        return volunteerRepository.findAll();
    }

    @Override
    public Optional<Volunteer> handle(GetVolunteerByIdQuery query) {
        return volunteerRepository.findById(query.volunteerId());
    }

    @Override
    public Optional<Volunteer> handle(GetVolunteerByUserIdQuery query) {
        return volunteerRepository.findByUserId(query.userId());
    }
}

