package com.lostin.auth.jwt.core;

/**
    DTO record for JWT tokens (only access tokens, Opaque Strings for refresh tokens)
 */
public record JWToken(String value) {

    public static JWToken from(String value){
        return new JWToken(value);
    }
}
