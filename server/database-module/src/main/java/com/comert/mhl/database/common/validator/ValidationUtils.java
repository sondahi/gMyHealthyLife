package com.comert.mhl.database.common.validator;

import com.comert.mhl.database.common.exception.EntityFieldNotValidException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Iterator;
import java.util.Set;

@ApplicationScoped
public class ValidationUtils {

    private Validator validator;

    public ValidationUtils() {
    }

    @Inject
    public ValidationUtils(Validator validator) {
        this.validator = validator;
    }

    public <T> void validateEntityFields(final T entity) throws EntityFieldNotValidException {
        final Set<ConstraintViolation<T>> errors = validator.validate(entity);
        final Iterator<ConstraintViolation<T>> itErrors = errors.iterator();
        if (itErrors.hasNext()) {
            final ConstraintViolation<T> violation = itErrors.next();
            throw new EntityFieldNotValidException(violation.getMessage(), violation.getPropertyPath().toString());
        }
    }
}
