package com.lostin.auth.model.core.user;

import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.util.validation.JakartaValidator;
import com.lostin.auth.util.validation.annotation.ValidPassword;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public record Password(
        @ValidPassword
        String value
) {

    public Password(String value) {
        this.value = value;
        Set<ConstraintViolation<Password>> violations = JakartaValidator.validator().validate(this);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Password> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            throw new ValidationException("PASSWORD_VALIDATION_ERROR", sb.toString());
        }

    }

}
