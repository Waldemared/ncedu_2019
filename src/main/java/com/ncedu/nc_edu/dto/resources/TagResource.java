package com.ncedu.nc_edu.dto.resources;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
public class TagResource extends RepresentationModel<TagResource> {
    @NotBlank(message = "Name cannot be empty")
    private String name;
}