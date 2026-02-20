package com.lostin.auth.request_response.basic_auth_flow.request;

import com.lostin.auth.util.validation.annotation.ValidEmail;
import com.lostin.auth.util.validation.annotation.ValidPassword;

public record BasicAuthLoginRequest(
        @ValidEmail
        String email,
        @ValidPassword
        String password
) {}
