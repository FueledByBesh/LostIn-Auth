package com.lostin.auth.exception;

public class ValidationException extends ServerError {
    public ValidationException(String error,String message) {
        super(error,message);
    }
    public ValidationException() {
        super();
    }
}
