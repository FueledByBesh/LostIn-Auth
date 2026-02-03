package com.lostin.auth.model.core.auth_token;


public record RefreshToken (
        String value
){
    public static RefreshToken from(String value){
        return new RefreshToken(value);
    }
}
