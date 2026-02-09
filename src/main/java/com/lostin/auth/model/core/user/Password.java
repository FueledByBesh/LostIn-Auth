package com.lostin.auth.model.core.user;

import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.util.abstracts.Validatable;
import com.lostin.auth.util.validator.JakartaValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Optional;
import java.util.Set;

public record Password(
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                message = "Password must contain at least one number, one uppercase letter, one lowercase letter and one symbol"
        )
        String value
) implements Validatable {
    public void validate() throws ValidationException {
        this.getViolations().ifPresent(value -> {
            throw new ValidationException("PASSWORD_VALIDATION_ERROR", value);
        });
    }

    @Override
    public Optional<String> getViolations() {
        Set<ConstraintViolation<Password>> violations = JakartaValidator.validator().validate(this);
        if (violations.isEmpty()) return Optional.empty();

        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<Password> violation : violations) {
            sb.append(violation.getMessage()).append("; ");
        }
        return Optional.of(sb.toString());
    }

    public static Password validated(String value) throws ValidationException {
        Password password = new Password(value);
        password.validate();
        return password;
    }
}
