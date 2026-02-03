package com.lostin.auth.request_response.basic_auth_flow.request;

import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.Password;
import jakarta.validation.constraints.*;

public record BasicAuthLoginRequest(
        Email email,
        Password password
) {
    public BasicAuthLoginRequest(
            Email email,
            Password password
    ) {
        this.email = email;
        this.password = password;
        StringBuilder violations = new StringBuilder();
        email.getViolations().ifPresent(violations::append);
        password.getViolations().ifPresent(violations::append);
        if (!violations.isEmpty()) {
            throw new BadRequestException("REQUEST_VALIDATION_ERROR", violations.toString());
        }
    }
}
