package com.lostin.auth.request_response.basic_auth_flow.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.Password;
import com.lostin.auth.util.abstracts.UserRequestValidator;
import com.lostin.auth.util.abstracts.Validatable;
import jakarta.validation.constraints.*;

import java.util.Optional;

public record BasicAuthLoginRequest(
        Email email,
        Password password
)implements UserRequestValidator {
    @JsonCreator
    public BasicAuthLoginRequest(
            String email,
            String password
    ) {
        this(new Email(email), new Password(password));
    }

    @Override
    public void validate() throws ValidationException {
        StringBuilder violations = new StringBuilder();
        email.getViolations().ifPresent(violations::append);
        password.getViolations().ifPresent(violations::append);
        if(!violations.isEmpty()){
            throw new BadRequestException("REQUEST_VALIDATION_ERROR",violations.toString());
        }
    }
}
