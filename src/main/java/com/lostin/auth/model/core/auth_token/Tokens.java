package com.lostin.auth.model.core.auth_token;

public record Tokens(
        AccessToken accessToken,
        RefreshToken refreshToken
) {

    public static Tokens from(AccessToken accessToken, RefreshToken refreshToken){
        return new Tokens(accessToken,refreshToken);
    }
}
