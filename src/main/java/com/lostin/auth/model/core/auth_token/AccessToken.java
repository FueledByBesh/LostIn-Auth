package com.lostin.auth.model.core.auth_token;

import com.lostin.auth.jwt.core.JWToken;

public record AccessToken(
        JWToken value
) {}
