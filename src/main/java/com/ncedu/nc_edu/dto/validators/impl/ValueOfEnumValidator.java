package com.ncedu.nc_edu.dto.validators.impl;

import com.ncedu.nc_edu.dto.validators.ValueOfEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return value == null || acceptedValues.contains(value.toString());
    }

    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.value().getEnumConstants())
                .map(Enum::name).collect(Collectors.toList());
    }
}
