package com.acme.center.volunpath_backend.volunteers.infrastructure.persistence.jpa.repositories;

import com.acme.center.volunpath_backend.volunteers.domain.model.aggregates.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Optional<Volunteer> findByUserId(Long userId);
    Optional<Volunteer> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserId(Long userId);
}

