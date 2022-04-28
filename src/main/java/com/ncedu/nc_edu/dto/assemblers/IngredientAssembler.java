package com.ncedu.nc_edu.dto.assemblers;

import com.ncedu.nc_edu.dto.resources.IngredientResource;
import com.ncedu.nc_edu.models.Ingredient;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class IngredientAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientResource> {
    public IngredientAssembler() {
        super(Ingredient.class, IngredientResource.class);
    }

    @Override
    public IngredientResource toModel(Ingredient entity) {
        IngredientResource resource = new IngredientResource();

        resource.setId(entity.getId());
        resource.setName(entity.getName());

        return resource;
    }
}
