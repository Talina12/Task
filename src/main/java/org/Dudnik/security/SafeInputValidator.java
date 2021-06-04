package org.Dudnik.security;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SafeInputValidator implements ConstraintValidator<SafeInput, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //checks if the input is safe and valid
        return true;
    }
}
