package com.lostin.auth.model.core;

import com.lostin.auth.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public record Password(
        String value
) {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public Password{
        Set<ConstraintViolation<Password>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Password> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            throw new ValidationException("PASSWORD_VALIDATION_ERROR",sb.toString());
        }
    }
}
