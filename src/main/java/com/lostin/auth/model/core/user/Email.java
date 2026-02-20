package com.lostin.auth.model.core.user;


import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.util.validation.JakartaValidator;
import com.lostin.auth.util.validation.annotation.ValidEmail;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public record Email(
        @ValidEmail
        String value
) {

    public Email(String value) {
        this.value = value;
        Set<ConstraintViolation<Email>> violations = JakartaValidator.validator().validate(this);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Email> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            throw new ValidationException("EMAIL_VALIDATION_ERROR", sb.toString());
        }
    }

}
