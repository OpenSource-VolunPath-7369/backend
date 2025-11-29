package com.acme.center.volunpath_backend.iam.interfaces.rest;

import com.acme.center.volunpath_backend.iam.domain.model.queries.GetAllUsersQuery;
import com.acme.center.volunpath_backend.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.center.volunpath_backend.iam.domain.services.UserCommandService;
import com.acme.center.volunpath_backend.iam.domain.services.UserQueryService;
import com.acme.center.volunpath_backend.iam.interfaces.rest.resources.UpdateUserResource;
import com.acme.center.volunpath_backend.iam.interfaces.rest.resources.UserResource;
import com.acme.center.volunpath_backend.iam.interfaces.rest.transform.UpdateUserCommandFromResourceAssembler;
import com.acme.center.volunpath_backend.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Users Controller
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var users = userQueryService.handle(new GetAllUsersQuery());
        var resources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long id) {
        var user = userQueryService.handle(new GetUserByIdQuery(id));
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    public ResponseEntity<UserResource> updateUser(@PathVariable Long id, @RequestBody UpdateUserResource resource) {
        var command = UpdateUserCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var user = userCommandService.handle(command);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }
}

