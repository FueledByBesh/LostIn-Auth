package com.lostin.auth.model.core.oauth_flow;

import com.lostin.auth.exception.ValidationException;

public enum CodeChallengeMethod {
    S256;

    public static CodeChallengeMethod from(String value) throws ValidationException {
        return switch (value){
            case "S256" -> S256;
            case "plain" -> throw new ValidationException("VALIDATION_ERROR","plain code challenge method is not supported");
            default -> throw new ValidationException("VALIDATION_ERROR","Invalid code challenge method: " + value);
        };
    }
}
