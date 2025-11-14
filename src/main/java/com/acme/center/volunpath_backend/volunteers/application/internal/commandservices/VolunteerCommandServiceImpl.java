package com.acme.center.volunpath_backend.volunteers.application.internal.commandservices;

import com.acme.center.volunpath_backend.volunteers.domain.model.aggregates.Volunteer;
import com.acme.center.volunpath_backend.volunteers.domain.model.commands.CreateVolunteerCommand;
import com.acme.center.volunpath_backend.volunteers.domain.model.commands.UpdateVolunteerCommand;
import com.acme.center.volunpath_backend.volunteers.domain.services.VolunteerCommandService;
import com.acme.center.volunpath_backend.volunteers.infrastructure.persistence.jpa.repositories.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Volunteer Command Service Implementation
 */
@Service
public class VolunteerCommandServiceImpl implements VolunteerCommandService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerCommandServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public Optional<Volunteer> handle(CreateVolunteerCommand command) {
        if (volunteerRepository.existsByEmail(command.email())) {
            throw new RuntimeException("Volunteer with this email already exists");
        }
        if (command.userId() != null && volunteerRepository.existsByUserId(command.userId())) {
            throw new RuntimeException("Volunteer with this user ID already exists");
        }

        var volunteer = new Volunteer(
                command.name(),
                command.email(),
                command.avatar(),
                command.bio(),
                command.location(),
                command.userId()
        );

        if (command.skills() != null) {
            volunteer.addSkills(command.skills());
        }

        volunteerRepository.save(volunteer);
        return Optional.of(volunteer);
    }

    @Override
    public Optional<Volunteer> handle(UpdateVolunteerCommand command) {
        var volunteer = volunteerRepository.findById(command.volunteerId())
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        if (command.name() != null) volunteer.setName(command.name());
        if (command.email() != null) volunteer.setEmail(command.email());
        if (command.avatar() != null) volunteer.setAvatar(command.avatar());
        if (command.bio() != null) volunteer.setBio(command.bio());
        if (command.location() != null) volunteer.setLocation(command.location());
        if (command.skills() != null) {
            volunteer.getSkills().clear();
            volunteer.addSkills(command.skills());
        }

        volunteerRepository.save(volunteer);
        return Optional.of(volunteer);
    }

    @Override
    public void handle(Long volunteerId) {
        if (!volunteerRepository.existsById(volunteerId)) {
            throw new RuntimeException("Volunteer not found");
        }
        volunteerRepository.deleteById(volunteerId);
    }
}

