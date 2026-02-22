package com.lostin.auth.exception;

public class BadRequestException extends ServerError {
    public BadRequestException(){
        super();
    }
    public BadRequestException(String error,String message) {
        super(error,message);
    }
}
