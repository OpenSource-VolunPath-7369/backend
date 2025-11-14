package com.acme.center.volunpath_backend.organizations.infrastructure.persistence.jpa.repositories;

import com.acme.center.volunpath_backend.organizations.domain.model.aggregates.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByUserId(Long userId);
    Optional<Organization> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserId(Long userId);
}

