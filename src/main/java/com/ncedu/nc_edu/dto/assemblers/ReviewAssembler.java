package com.ncedu.nc_edu.dto.assemblers;

import com.ncedu.nc_edu.controllers.RecipeController;
import com.ncedu.nc_edu.controllers.ReviewController;
import com.ncedu.nc_edu.controllers.UserController;
import com.ncedu.nc_edu.dto.resources.ReviewResource;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.Review;
import com.ncedu.nc_edu.security.SecurityAccessResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class ReviewAssembler extends RepresentationModelAssemblerSupport<Review, ReviewResource> {
    private final SecurityAccessResolver securityAccessResolver;

    public ReviewAssembler(@Autowired SecurityAccessResolver securityAccessResolver) {
        super(Review.class, ReviewResource.class);
        this.securityAccessResolver = securityAccessResolver;
    }

    @Override
    public ReviewResource toModel(Review entity) {
        ReviewResource resource = new ReviewResource();
        resource.setId(entity.getId());
        resource.setCreated_on(entity.getCreated_on());
        resource.setRating(entity.getRating());
        resource.setReviewText(entity.getReviewText());

        resource.add(linkTo(methodOn(ReviewController.class).getById(entity.getId())).withSelfRel().withType("GET"));
        resource.add(linkTo(methodOn(RecipeController.class).getById(null, entity.getRecipe().getId())).withRel("recipe").withType("GET"));
        resource.add(linkTo(methodOn(UserController.class).getById(entity.getUser().getId())).withRel("user").withType("GET"));

        if (entity.getRecipe().getState().equals(Recipe.State.PUBLISHED)) {
            if (securityAccessResolver.isReviewOwnerOrGranted(entity.getId())) {
                resource.add(linkTo(methodOn(ReviewController.class).deleteReview(entity.getId())).withRel("delete").withType("DELETE"));
            }
            if (securityAccessResolver.isReviewOwner(entity.getId())) {
                resource.add(linkTo(methodOn(ReviewController.class).updateReview(entity.getId(), null)).withRel("update").withType("PUT"));
            }
        }
        return resource;
    }
}
