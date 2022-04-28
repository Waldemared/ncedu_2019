package com.ncedu.nc_edu.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationCredentials {

    @Email(message = "must be a valid email")
    String email;

    @Size(min = 8, max = 32, message = "must be minimum 8, maximum 32 symbols")
    String password;
}
