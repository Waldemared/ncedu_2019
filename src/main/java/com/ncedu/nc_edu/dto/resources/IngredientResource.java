package com.ncedu.nc_edu.dto.resources;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class IngredientResource extends RepresentationModel<IngredientResource> {
    private UUID id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 30, min = 1)
    private String name;
}
