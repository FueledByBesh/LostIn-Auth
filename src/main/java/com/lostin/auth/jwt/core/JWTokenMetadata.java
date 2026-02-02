package com.lostin.auth.jwt.core;

import com.lostin.auth.exception.ServerError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@Builder
public record JWTokenMetadata(
        @NotNull(message = "Token id is required")
        @NotBlank(message = "Token id is required")
        String tokenId,
        @NotNull(message = "Subject is required")
        @NotBlank(message = "Subject is required")
        String subject,
        @NotNull(message = "Issuer is required")
        @NotBlank(message = "Issuer is required")
        String issuer,
        @NotNull(message = "Audience is required")
        @NotBlank(message = "Audience is required")
        String audience,
        @Positive(message = "Seconds cannot be negative")
        @Min(value = 30,message = "Expires after must be greater or equal than 30 seconds")
        Long expiresAfter
) {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = factory.getValidator();

    public JWTokenMetadata(
            String tokenId,
            String subject,
            String issuer,
            String audience,
            Long expiresAfter
    ){
        this.tokenId = tokenId;
        this.subject = subject;
        this.issuer = issuer;
        this.audience = audience;
        this.expiresAfter = expiresAfter;
        Set<ConstraintViolation<JWTokenMetadata>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<JWTokenMetadata> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            log.error("Token metadata validation failed: {}",sb);
            throw new ServerError();
        }
    }
}
