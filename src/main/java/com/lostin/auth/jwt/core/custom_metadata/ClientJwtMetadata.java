package com.lostin.auth.jwt.core.custom_metadata;

import com.lostin.auth.exception.ServerError;
import com.lostin.auth.jwt.core.JWTokenMetadata;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@SuperBuilder
public class ClientJwtMetadata extends JWTokenMetadata {
    @NotBlank(message = "Scopes are required")
    private String scopes;


    @Override
    public Map<String, Object> getClaimMap() {
        Map<String, Object> claims = new HashMap<>(super.getClaimMap());
        claims.put("scopes", scopes);
        return claims;
    }

    @Override
    public void validate() {
        Set<ConstraintViolation<ClientJwtMetadata>> violationSet = validator.validate(this);
        if (violationSet.isEmpty()) {
            return;
        }
        StringBuilder finalViolations = new StringBuilder("Validation error: ");
        violationSet.forEach(violation ->
                finalViolations.append(violation.getMessage()).append("; ")
        );
        log.error(finalViolations.toString());
        throw new ServerError();
    }
}
