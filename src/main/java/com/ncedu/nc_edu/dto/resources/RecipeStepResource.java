package com.ncedu.nc_edu.dto.resources;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecipeStepResource extends RepresentationModel<RecipeStepResource> {
    private UUID id;

    private String description;

    private UUID picture;
}
