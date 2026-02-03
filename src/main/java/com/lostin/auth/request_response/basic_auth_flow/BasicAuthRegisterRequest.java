package com.lostin.auth.request_response.basic_auth_flow;

import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.Password;
import com.lostin.auth.model.core.user.Username;

public record BasicAuthRegisterRequest (
    Email email,
    Password password,
    Username username
){
    public BasicAuthRegisterRequest(
            Email email,
            Password password,
            Username username
    ) {
        this.email = email;
        this.password = password;
        this.username = username;
        StringBuilder violations = new StringBuilder();
        email.getViolations().ifPresent(violations::append);
        password.getViolations().ifPresent(violations::append);
        username.getViolations().ifPresent(violations::append);
        if(!violations.isEmpty()){
            throw new BadRequestException("REQUEST_VALIDATION_ERROR",violations.toString());
        }
    }
}
