package com.ncedu.nc_edu.dto.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.UUID;

@Data
public class ReviewResource extends RepresentationModel<ReviewResource> {
    private UUID id;

    @JsonFormat(pattern = "dd.MM.yyyy", timezone = "Europe/Moscow")
    @PastOrPresent(message = "Creation date must be past or present")
    private Date created_on;

    @NotNull(message = "Rating value must be specified")
    @Positive(message = "Rating must be positive")
    @Max(value = 5, message = "Rating must be greater or equals to 5")
    private Float rating;

    @NotBlank(message = "Review's text must be not blank")
    private String reviewText;
}
