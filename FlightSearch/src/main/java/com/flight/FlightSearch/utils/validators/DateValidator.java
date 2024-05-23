package com.flight.FlightSearch.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateValidatorConstraint, String> {

    @Override
    public void initialize(DateValidatorConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        String regex = "^20\\d{2}-(0[1-9]|1[0-2])-[0-3][0-9]T([01][0-9]|2[0-4]):[0-5][0-9]:[0-5][0-9]$";
        return value.matches(regex);
    }

}
