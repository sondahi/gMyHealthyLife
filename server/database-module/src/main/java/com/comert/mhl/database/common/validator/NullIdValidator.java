package com.comert.mhl.database.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullIdValidator implements ConstraintValidator<NullId,Integer> {

    @Override
    public void initialize(NullId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return false;
    }
}
