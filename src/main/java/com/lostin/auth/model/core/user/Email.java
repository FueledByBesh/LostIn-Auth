package com.lostin.auth.model.core.user;


import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.util.abstracts.Validatable;
import com.lostin.auth.util.validator.JakartaValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;
import java.util.Set;

public record Email(
        @NotBlank(message = "Email is required")
        @jakarta.validation.constraints.Email(message = "Invalid email format")
        String value
) implements Validatable {
    public void validate() throws ValidationException {
        getViolations().ifPresent(value -> {
            throw new ValidationException("EMAIL_VALIDATION_ERROR", value);
        });
    }

    @Override
    public Optional<String> getViolations() {
        Set<ConstraintViolation<Email>> violations = JakartaValidator.validator().validate(this);
        if (violations.isEmpty()) return Optional.empty();

        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<Email> violation : violations) {
            sb.append(violation.getMessage()).append("; ");
        }
        return Optional.of(sb.toString());
    }

    public static Email validated(String value) throws ValidationException {
        Email email = new Email(value);
        email.validate();
        return email;
    }
}
