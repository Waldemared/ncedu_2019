package com.ncedu.nc_edu.dto.resources;

import com.fasterxml.jackson.annotation.*;
import com.ncedu.nc_edu.dto.validators.ValueOfEnum;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.security.View;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResource extends RepresentationModel<UserResource> implements OwnableResource {
    @JsonView(View.User.class)
    private UUID id;

    @JsonView(View.User.class)
    @Size(min = 3, max = 64, message = "Username must be more than 3  and less than 64 characters")
    private String username;

    @JsonView({View.Owner.class, View.Moderator.class})
    @Email(message = "Email must be a valid email", regexp=".+@.+\\..+")
    private String email;

    @JsonView(View.Owner.class)
    @ValueOfEnum(value = User.Gender.class, message = "Gender must be any of MALE|FEMALE|UNKNOWN")
    private String gender;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonView(View.Owner.class)
    @Past(message = "Birthday must be in the past")
    @JsonFormat(pattern = "dd.MM.yyyy", timezone = "Europe/Moscow")
    private Date birthday;

    @JsonView(View.Owner.class)
    @PositiveOrZero(message = "Height must be positive or 0 for deletion")
    private Integer height;

    @JsonView(View.Owner.class)
    @PositiveOrZero(message = "Weight must be positive or 0 for deletion")
    private Integer weight;

    @JsonIgnore
    @Override
    public UUID getOwnerId() {
        return this.id;
    }
}
