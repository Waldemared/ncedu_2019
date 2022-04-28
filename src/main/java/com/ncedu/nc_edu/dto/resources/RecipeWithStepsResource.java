package com.ncedu.nc_edu.dto.resources;

import lombok.Data;

import java.util.List;

@Data
public class RecipeWithStepsResource {
    private RecipeResource info;
    private List<RecipeStepResource> steps;

    public RecipeWithStepsResource(RecipeResource info, List<RecipeStepResource> steps) {
        this.info = info;
        this.steps = steps;
    }
}
