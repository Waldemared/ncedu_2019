package com.ncedu.nc_edu.dto.assemblers;

import com.ncedu.nc_edu.dto.resources.RecipeIngredientResource;
import com.ncedu.nc_edu.models.IngredientsRecipes;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RecipeIngredientAssembler extends RepresentationModelAssemblerSupport<IngredientsRecipes, RecipeIngredientResource> {
    public RecipeIngredientAssembler() {
        super(IngredientsRecipes.class, RecipeIngredientResource.class);
    }

    @Override
    public RecipeIngredientResource toModel(IngredientsRecipes entity) {
        RecipeIngredientResource resource = new RecipeIngredientResource();

        resource.setId(entity.getIngredient().getId());
        resource.setName(entity.getIngredient().getName());
        resource.setValue(entity.getValue());
        resource.setValueType(entity.getValueType());

        return resource;
    }
}
