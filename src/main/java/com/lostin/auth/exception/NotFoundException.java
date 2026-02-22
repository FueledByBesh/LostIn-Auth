package com.lostin.auth.exception;

public class NotFoundException extends ServerError {
    public NotFoundException(String error,String message) {
        super(error,message);
    }
    public NotFoundException() {
        super();
    }
}
