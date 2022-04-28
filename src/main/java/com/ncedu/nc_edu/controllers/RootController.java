package com.ncedu.nc_edu.controllers;

import com.ncedu.nc_edu.security.SecurityAccessResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RootController {
    private SecurityAccessResolver securityAccessResolver;

    public RootController(@Autowired SecurityAccessResolver securityAccessResolver) {
        this.securityAccessResolver = securityAccessResolver;
    }

    @GetMapping("/")
    public ResponseEntity<EntityModel<Map<String, Object>>> root() {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("authenticated", securityAccessResolver.getUser() != null);
        EntityModel response = new EntityModel<>(responseMap);
        response.add(linkTo(methodOn(RootController.class).root()).withSelfRel().withType("GET"));
        response.add(linkTo(methodOn(RecipeController.class).getAll(null, null, null)).withRel("recipes").withType("GET"));
        response.add(linkTo(methodOn(RecipeController.class).search(null, null, null, null)).withRel("recipe search").withType("GET"));
        if (securityAccessResolver.getUser() == null) {
            response.add(linkTo(methodOn(UserController.class).add(null)).withRel("register").withType("GET"));
        } else {
            response.add(linkTo(methodOn(UserController.class).getAuthenticatedUser(null)).withRel("me").withType("GET"));
            if (securityAccessResolver.isModerator()) {
                response.add(linkTo(methodOn(ModeratorController.class).moderatorRoot()).withRel("moderator").withType("GET"));
            }
            if (securityAccessResolver.isAdmin()) {
                response.add(linkTo(methodOn(AdminController.class).adminRoot()).withRel("admin").withType("GET"));
            }
        }
        return ResponseEntity.ok(response);
    }
}
