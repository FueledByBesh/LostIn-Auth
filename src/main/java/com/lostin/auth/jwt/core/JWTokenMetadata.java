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
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
@SuperBuilder
@Getter
public class JWTokenMetadata{

    @NotBlank(message = "Token id is required")
    private String tokenId;
    @NotBlank(message = "Subject is required")
    private String subject;
    @NotBlank(message = "Issuer is required")
    private String issuer;
    @NotBlank(message = "Audience is required")
    private String audience;
    @NotNull(message = "Expires after is required")
    @Positive(message = "Seconds cannot be negative")
    @Min(value = 30,message = "Expires after must be greater or equal than 30 seconds")
    private Long expiresAfter;

    protected static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    protected static Validator validator = factory.getValidator();

    /**
     * @return Immutable default claim map
     */
    public Map<String, Object> getClaimMap(){
        return Map.of("jti",tokenId,"sub",subject,"iss",issuer,"aud",audience);
    }

    public Set<ConstraintViolation<JWTokenMetadata>> violations(){
        return validator.validate(this);
    }
    public void validate(){
        if(violations().isEmpty()) return;
        StringBuilder sb = new StringBuilder("Validation error: ");
        violations().forEach(violation ->
            sb.append(violation.getMessage()).append("; ")
        );
        log.error(sb.toString());
        throw new ServerError();
    }
}
