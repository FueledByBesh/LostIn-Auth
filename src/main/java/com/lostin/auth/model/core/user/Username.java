package com.lostin.auth.model.core.user;

import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.util.abstracts.Validatable;
import com.lostin.auth.util.validator.JakartaValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;
import java.util.Set;

public record Username(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String value
) implements Validatable {


    @Override
    public void validate() throws ValidationException {
        getViolations().ifPresent(value -> {
            throw new ValidationException("USERNAME_VALIDATION_ERROR", value);
        });
    }

    @Override
    public Optional<String> getViolations() {
        Set<ConstraintViolation<Username>> violations = JakartaValidator.validator().validate(this);
        if (violations.isEmpty()) return Optional.empty();

        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<Username> violation : violations) {
            sb.append(violation.getMessage()).append("; ");
        }
        return Optional.of(sb.toString());
    }
}
