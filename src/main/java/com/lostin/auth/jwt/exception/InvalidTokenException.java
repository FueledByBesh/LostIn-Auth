package com.lostin.auth.jwt.exception;

import com.lostin.auth.exception.ServerError;

public class InvalidTokenException extends ServerError {
    public InvalidTokenException(String message) {
        super("INVALID_TOKEN",message);
    }
}
