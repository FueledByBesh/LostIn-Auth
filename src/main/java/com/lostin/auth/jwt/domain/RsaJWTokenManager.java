package com.lostin.auth.jwt.domain;


import com.lostin.auth.jwt.core.JWToken;
import com.lostin.auth.jwt.core.JWTokenMetadata;

import java.util.Map;

public interface RsaJWTokenManager {

    JWToken generateAndSign(JWTokenMetadata metadata);
    JWToken generateWithCustomClaimsAndSign(JWTokenMetadata metadata, Map<String, Object> customClaims);
    boolean validateToken(JWToken token);

}
