package com.acme.center.volunpath_backend.publications.interfaces.rest;

import com.acme.center.volunpath_backend.publications.domain.model.aggregates.Enrollment;
import com.acme.center.volunpath_backend.publications.domain.model.queries.GetEnrollmentsByPublicationIdQuery;
import com.acme.center.volunpath_backend.publications.domain.services.EnrollmentCommandService;
import com.acme.center.volunpath_backend.publications.domain.services.EnrollmentQueryService;
import com.acme.center.volunpath_backend.publications.interfaces.rest.resources.CreateEnrollmentResource;
import com.acme.center.volunpath_backend.publications.interfaces.rest.resources.EnrollmentResource;
import com.acme.center.volunpath_backend.publications.interfaces.rest.transform.CreateEnrollmentCommandFromResourceAssembler;
import com.acme.center.volunpath_backend.publications.interfaces.rest.transform.EnrollmentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Enrollments Controller
 */
@RestController
@RequestMapping(value = "/api/v1/publications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Enrollments", description = "Enrollment Management Endpoints")
public class EnrollmentsController {
    private final EnrollmentCommandService enrollmentCommandService;
    private final EnrollmentQueryService enrollmentQueryService;

    public EnrollmentsController(
            EnrollmentCommandService enrollmentCommandService,
            EnrollmentQueryService enrollmentQueryService) {
        this.enrollmentCommandService = enrollmentCommandService;
        this.enrollmentQueryService = enrollmentQueryService;
    }

    @PostMapping("/{publicationId}/enrollments")
    @Operation(summary = "Register a volunteer for a publication")
    public ResponseEntity<?> createEnrollment(
            @PathVariable Long publicationId,
            @RequestBody CreateEnrollmentResource resource) {
        try {
            // Ensure publicationId in path matches resource
            var command = CreateEnrollmentCommandFromResourceAssembler.toCommandFromResource(
                    new CreateEnrollmentResource(publicationId, resource.volunteerId(), resource.volunteerName()));
            var enrollment = enrollmentCommandService.handle(command);
            if (enrollment.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            var enrollmentResource = EnrollmentResourceFromEntityAssembler.toResourceFromEntity(enrollment.get());
            return new ResponseEntity<>(enrollmentResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{publicationId}/enrollments")
    @Operation(summary = "Get all enrollments for a publication")
    public ResponseEntity<List<EnrollmentResource>> getEnrollmentsByPublicationId(@PathVariable Long publicationId) {
        var enrollments = enrollmentQueryService.handle(new GetEnrollmentsByPublicationIdQuery(publicationId));
        var resources = enrollments.stream()
                .map(EnrollmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{publicationId}/enrollments/check")
    @Operation(summary = "Check if a volunteer is enrolled in a publication")
    public ResponseEntity<Boolean> checkEnrollment(
            @PathVariable Long publicationId,
            @RequestParam Long volunteerId) {
        boolean isEnrolled = enrollmentQueryService.isVolunteerEnrolled(publicationId, volunteerId);
        return ResponseEntity.ok(isEnrolled);
    }

    @DeleteMapping("/{publicationId}/enrollments/{enrollmentId}")
    @Operation(summary = "Cancel an enrollment")
    public ResponseEntity<Void> deleteEnrollment(
            @PathVariable Long publicationId,
            @PathVariable Long enrollmentId) {
        try {
            enrollmentCommandService.delete(enrollmentId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

