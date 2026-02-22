package com.lostin.auth.exception;

public class ConflictException extends ServerError{
    public ConflictException(String error,String message) {
        super(error,message);
    }
    public ConflictException() {
        super();
    }
}
