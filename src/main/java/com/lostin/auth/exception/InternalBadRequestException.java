package com.lostin.auth.exception;

public class InternalBadRequestException extends ServerError {
    public InternalBadRequestException(String message) {
        super(message);
    }
    public InternalBadRequestException() {
        super();
    }
}
