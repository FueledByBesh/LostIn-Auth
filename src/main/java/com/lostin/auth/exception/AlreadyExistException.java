package com.lostin.auth.exception;

public class AlreadyExistException extends ServerError {
    public AlreadyExistException(String error,String message) {
        super(error,message);
    }
}
