package com.ncedu.nc_edu.controllers;

import com.ncedu.nc_edu.dto.UserRegistrationCredentials;
import com.ncedu.nc_edu.dto.assemblers.RecipeAssembler;
import com.ncedu.nc_edu.dto.assemblers.ReviewAssembler;
import com.ncedu.nc_edu.dto.assemblers.UserAssembler;
import com.ncedu.nc_edu.dto.resources.RecipeResource;
import com.ncedu.nc_edu.dto.resources.ReviewResource;
import com.ncedu.nc_edu.dto.resources.UserResource;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.security.CustomUserDetails;
import com.ncedu.nc_edu.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@Validated
public class UserController {
    private final UserService userService;
    private final UserAssembler userAssembler;
    private final ReviewAssembler reviewAssembler;
    private final RecipeAssembler recipeAssembler;

    public UserController(@Autowired UserService userService,
                          @Autowired UserAssembler userAssembler,
                          @Autowired ReviewAssembler reviewAssembler,
                          @Autowired RecipeAssembler recipeAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
        this.reviewAssembler = reviewAssembler;
        this.recipeAssembler = recipeAssembler;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RepresentationModel<UserResource>> add(@RequestBody @Valid UserRegistrationCredentials credentials) {
        User user = userService.registerUser(credentials.getEmail(), credentials.getPassword());
        UserResource userResource = userAssembler.toModel(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResource);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<CollectionModel<UserResource>> getAll() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(userAssembler.toCollectionModel(users));
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<RepresentationModel<UserResource>> getById(@PathVariable UUID id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(userAssembler.toModel(user));
    }

    @GetMapping(value = "/users/{id}/recipes")
    public ResponseEntity<CollectionModel<RecipeResource>> getRecipes(@PathVariable UUID id) {
        return ResponseEntity.ok(recipeAssembler.toCollectionModel(userService.getRecipesById(id)));
    }

    @GetMapping(value = "/users/@me")
    public ResponseEntity<RepresentationModel<UserResource>> getAuthenticatedUser(Authentication auth) {
        return ResponseEntity.ok(userAssembler.toModel(((CustomUserDetails) auth.getPrincipal()).getUser()));
    }

    @GetMapping(value = "/users/{id}/reviews")
    public ResponseEntity<CollectionModel<ReviewResource>> getUserReviews(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewAssembler.toCollectionModel(userService.getReviewsById(id)));
    }

    @PatchMapping(value = "/users/{id}")
    public ResponseEntity<RepresentationModel<UserResource>> update(
            @PathVariable UUID id,
            @Valid @RequestBody UserResource userResource) {
        log.info(userResource.toString());
        userResource.setId(id);
        return ResponseEntity.ok(userAssembler.toModel(userService.update(userResource)));
    }
}