package com.lostin.auth.jwt.domain;


import com.lostin.auth.jwt.core.JWToken;
import com.lostin.auth.jwt.core.JWTokenMetadata;
import com.lostin.auth.jwt.exception.InvalidTokenException;

public interface RsaJWTokenManager {

    JWToken generateAndSign(JWTokenMetadata metadata);
    void validateToken(JWToken token) throws InvalidTokenException;

}
