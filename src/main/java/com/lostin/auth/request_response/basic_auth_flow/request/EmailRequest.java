package com.lostin.auth.request_response.basic_auth_flow.request;

import com.lostin.auth.util.validation.annotation.ValidEmail;

public record EmailRequest(
        @ValidEmail
        String email
) {
}
