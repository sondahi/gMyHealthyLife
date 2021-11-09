package com.comert.mhl.web.resource.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target ({METHOD,FIELD,ANNOTATION_TYPE})
@Retention (RUNTIME)
@Constraint (validatedBy= EmailConstraintValidator.class )
@Documented
public @interface Email {

    String message() default "Email is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
