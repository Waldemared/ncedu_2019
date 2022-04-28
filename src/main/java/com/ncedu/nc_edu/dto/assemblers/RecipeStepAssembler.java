package com.ncedu.nc_edu.dto.assemblers;

import com.ncedu.nc_edu.controllers.PictureController;
import com.ncedu.nc_edu.controllers.RecipeController;
import com.ncedu.nc_edu.dto.resources.RecipeStepResource;
import com.ncedu.nc_edu.models.RecipeStep;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RecipeStepAssembler extends RepresentationModelAssemblerSupport<RecipeStep, RecipeStepResource> {
    public RecipeStepAssembler() {
        super(RecipeStep.class, RecipeStepResource.class);
    }

    @Override
    public RecipeStepResource toModel(RecipeStep entity) {
        RecipeStepResource resource = new RecipeStepResource();

        resource.setId(entity.getId());
        resource.setDescription(entity.getDescription());
        resource.setPicture(entity.getPicture());

        resource.add(linkTo(methodOn(RecipeController.class).getRecipeSteps(entity.getId())).withRel("recipe"));

        if (entity.getPicture() != null) {
            resource.add(linkTo(methodOn(PictureController.class).get(entity.getPicture())).withRel("picture"));
        }

        return resource;
    }
}
