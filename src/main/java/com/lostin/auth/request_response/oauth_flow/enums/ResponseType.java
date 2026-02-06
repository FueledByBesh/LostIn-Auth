package com.lostin.auth.request_response.oauth_flow.enums;

import com.lostin.auth.exception.ValidationException;

public enum ResponseType {
    CODE,
    TOKEN;

    public ResponseType from(String value) throws ValidationException {
        return switch (value){
            case "code" -> CODE;
            case "token" -> TOKEN;
            default -> throw new ValidationException("VALIDATION_ERROR","Invalid response type: " + value);
        };
    }
}
