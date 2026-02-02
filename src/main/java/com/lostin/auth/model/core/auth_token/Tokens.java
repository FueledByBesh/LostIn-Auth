package com.lostin.auth.model.core.auth_token;

public record Tokens(
        AccessToken accessToken,
        RefreshToken refreshToken
) {
}
