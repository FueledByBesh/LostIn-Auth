package com.lostin.auth.model.core.user;

import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.util.validation.JakartaValidator;
import com.lostin.auth.util.validation.annotation.ValidUsername;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

public record Username(
        @ValidUsername
        String value
) {

    public Username(String value) {
        this.value = value;

        Set<ConstraintViolation<Username>> violations = JakartaValidator.validator().validate(this);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Username> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            throw new ValidationException("USERNAME_VALIDATION_ERROR", sb.toString());
        }

    }

}
