package com.lostin.auth.model.core.user;

import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.util.abstracts.Validatable;
import com.lostin.auth.util.validator.JakartaValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public record UserId(
        @NotNull(message = "User ID is required")
        UUID value
) implements Validatable {
    @Override
    public void validate() throws ValidationException {
        getViolations().ifPresent(value -> {
            throw new ValidationException("USER_ID_VALIDATION_ERROR", value);
        });
    }

    @Override
    public Optional<String> getViolations() {
        Set<ConstraintViolation<UserId>> violations = JakartaValidator.validator().validate(this);
        if (violations.isEmpty()) return Optional.empty();
        StringBuilder sb = new StringBuilder();
        violations.forEach(violation -> sb.append(violation.getMessage()).append("; "));
        return Optional.of(sb.toString());
    }

    public static UserId validated(UUID value) throws ValidationException {
        UserId userId = new UserId(value);
        userId.validate();
        return userId;
    }

    public static UserId validated(String value) throws ValidationException {
        UUID id;
        try {
            id = UUID.fromString(value);
        }catch (IllegalArgumentException e){
            throw new ValidationException("USER_ID_VALIDATION_ERROR","Invalid user id: " + value);
        }
        return validated(id);
    }
}
