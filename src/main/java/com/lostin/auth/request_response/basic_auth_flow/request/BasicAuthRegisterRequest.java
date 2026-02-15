package com.lostin.auth.request_response.basic_auth_flow.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.Password;
import com.lostin.auth.model.core.user.Username;
import com.lostin.auth.util.abstracts.UserRequestValidator;

public record BasicAuthRegisterRequest (
    Email email,
    Password password,
    Username username
) implements UserRequestValidator {
    @JsonCreator
    public BasicAuthRegisterRequest(
            String email,
            String password,
            String username
    ) {
        this(new Email(email),new Password(password),new Username(username));
    }

    @Override
    public void validate() throws BadRequestException {
        StringBuilder violations = new StringBuilder();
        email.getViolations().ifPresent(violations::append);
        password.getViolations().ifPresent(violations::append);
        username.getViolations().ifPresent(violations::append);
        if(!violations.isEmpty()){
            throw new BadRequestException("REQUEST_VALIDATION_ERROR",violations.toString());
        }
    }
}
