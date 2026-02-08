package com.lostin.auth.model.core.oauth_flow;

import com.lostin.auth.exception.ValidationException;

public enum ResponseType {
    CODE,
    TOKEN;

    public static ResponseType from(String value) throws ValidationException {
        return switch (value){
            case "code" -> CODE;
            case "token" -> throw new ValidationException("VALIDATION_ERROR","token response type is not supported");
            default -> throw new ValidationException("VALIDATION_ERROR","Invalid response type: " + value);
        };
    }
}
