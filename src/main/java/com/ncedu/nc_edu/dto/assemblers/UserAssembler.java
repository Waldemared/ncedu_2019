package com.ncedu.nc_edu.dto.assemblers;

import com.ncedu.nc_edu.controllers.AdminController;
import com.ncedu.nc_edu.controllers.ModeratorController;
import com.ncedu.nc_edu.controllers.UserController;
import com.ncedu.nc_edu.dto.resources.UserResource;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.security.SecurityAccessResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserResource> {
    private Class<UserController> controllerClass;
    private SecurityAccessResolver securityAccessResolver;

    public UserAssembler(@Autowired SecurityAccessResolver securityAccessResolver) {
        super(User.class, UserResource.class);
        this.controllerClass = UserController.class;
        this.securityAccessResolver = securityAccessResolver;
    }

    @Override
    public UserResource toModel(User entity) {
        UserResource userResource = new UserResource();
        userResource.setId(entity.getId());
        userResource.setUsername(entity.getUsername());
        userResource.setEmail(entity.getEmail());
        userResource.setBirthday(entity.getBirthday());
        userResource.setGender(entity.getGender().toString());
        userResource.setHeight(entity.getHeight());
        userResource.setWeight(entity.getWeight());
        userResource.add(linkTo(methodOn(controllerClass).getById(entity.getId())).withSelfRel().withType("GET"));
        userResource.add(linkTo(methodOn(controllerClass).getRecipes(userResource.getId())).withRel("recipes").withType("GET"));
        userResource.add(linkTo(methodOn(controllerClass).getUserReviews(userResource.getId())).withRel("reviews").withType("GET"));

        if (securityAccessResolver.isSelf(entity.getId())) {
            userResource.add(linkTo(methodOn(controllerClass).update(entity.getId(), null)).withRel("update").withType("PUT"));
            if (securityAccessResolver.isModerator()) {
                userResource.add(linkTo(methodOn(ModeratorController.class).moderatorRoot()).withRel("moderator").withType("GET"));
            }
            if (securityAccessResolver.isAdmin()) {
                userResource.add(linkTo(methodOn(AdminController.class).adminRoot()).withRel("admin").withType("GET"));
            }
        }
        return userResource;
    }

    @Override
    public CollectionModel<UserResource> toCollectionModel(Iterable<? extends User> entities) {
        return super.toCollectionModel(entities);
    }
}
