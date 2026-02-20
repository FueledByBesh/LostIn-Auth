package com.lostin.auth.request_response.basic_auth_flow.request;

import com.lostin.auth.util.validation.annotation.ValidEmail;
import com.lostin.auth.util.validation.annotation.ValidPassword;
import com.lostin.auth.util.validation.annotation.ValidUsername;

public record BasicAuthRegisterRequest(
        @ValidEmail
        String email,
        @ValidPassword
        String password,
        @ValidUsername
        String username
) {
}
