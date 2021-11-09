package com.comert.mhl.web.resource.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailConstraintValidator implements ConstraintValidator<Email, String> {

    private Pattern pattern;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void initialize(Email email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        if(value == null)
            return true;
        else
            return pattern.matcher(value).matches();
    }
}
