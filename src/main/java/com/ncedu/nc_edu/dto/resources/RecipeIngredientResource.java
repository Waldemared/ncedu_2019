package com.ncedu.nc_edu.dto.resources;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecipeIngredientResource extends RepresentationModel<RecipeIngredientResource> {
    private UUID id;

    private String name;

    @NotBlank
    @Size(max = 16)
    private String valueType;

    private Float value;
}
