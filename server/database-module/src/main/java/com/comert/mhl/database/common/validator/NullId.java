package com.comert.mhl.database.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {NullIdValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface NullId {

    String message() default "Entity Id must be null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
