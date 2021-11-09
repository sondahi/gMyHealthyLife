package com.comert.mhl.web.controller.validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import java.util.regex.Pattern;

@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {

    private Pattern pattern;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }

        if(!pattern.matcher(value.toString()).matches()) {
            throw new ValidatorException(new FacesMessage (FacesMessage.SEVERITY_ERROR, "Email: Validation Error:", "Email is not valid."));
        }
    }

}
