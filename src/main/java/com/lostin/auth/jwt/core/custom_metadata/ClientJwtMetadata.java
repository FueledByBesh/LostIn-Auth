package com.lostin.auth.jwt.core.custom_metadata;

import com.lostin.auth.exception.ServerError;
import com.lostin.auth.jwt.core.JWTokenMetadata;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SuperBuilder
public class ClientJwtMetadata extends JWTokenMetadata {
    @NotNull(message = "Scopes are required")
    @NotBlank(message = "Scopes are required")
    private String scopes;

    public ClientJwtMetadata(String tokenId, String subject, String issuer, String audience, Long expiresAfter) {
        super(tokenId, subject, issuer, audience, expiresAfter);
        if(scopes==null || scopes.isBlank()){
            log.error("Scopes cannot be null or empty");
            throw new ServerError();
        }
    }

    @Override
    public Map<String,Object> getClaimMap(){
        Map<String, Object> claims = new HashMap<>(super.getClaimMap());
        claims.put("scopes",scopes);
        return claims;
    }
}
