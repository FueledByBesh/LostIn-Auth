package com.lostin.auth.exception;


import com.lostin.auth.request_response.ErrorResponse;

public class ServiceResponseException extends RuntimeException {

    int statusCode;
    String error;
    String message;

    public ServiceResponseException(int statusCode, String error,String message) {
        super(message);
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse toErrorResponse(){
        return new ErrorResponse(error,message);
    }
}
