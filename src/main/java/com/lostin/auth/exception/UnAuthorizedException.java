package com.lostin.auth.exception;

public class UnAuthorizedException extends ServerError{
    public UnAuthorizedException(String message) {
        super("UNAUTHORIZED",message);
    }
    public UnAuthorizedException() {
        super();
    }
}
