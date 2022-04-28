package com.ncedu.nc_edu;

import com.ncedu.nc_edu.dto.resources.UserResource;
import com.ncedu.nc_edu.dto.resources.UserResource;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserResourceValueOfEnumValidatorTest {
    @Test
    public void nullTest() {
        UserResource user = new UserResource();
        user.setGender(null);
        user.setPassword("123");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set violations = validator.validate(user);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void valueTest() {
        UserResource user = new UserResource();
        user.setGender("MALE");
        user.setPassword("123");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set violations = validator.validate(user);

        assertThat(violations.size()).isEqualTo(0);

        user.setGender("asd");
        violations = validator.validate(user);

        assertThat(violations.size()).isEqualTo(1);
    }
}
