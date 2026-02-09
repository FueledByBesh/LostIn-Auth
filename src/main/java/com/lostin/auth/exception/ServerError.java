package com.lostin.auth.exception;

import com.lostin.auth.request_response.ErrorResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
@Getter
//@SuperBuilder
public class ServerError extends RuntimeException {

    String error;
    String message;

    public ServerError(String error,String message) {
        super(message);
        this.error = error;
        this.message = message;
    }
    public ServerError(){
        super("INTERNAL_SERVER_ERROR");
        this.error = "INTERNAL_SERVER_ERROR";
        this.message = "Something unexpected happened";
    }
    public ServerError(String message){
        super(message);
        this.error = "INTERNAL_SERVER_ERROR";
        this.message = message;
    }

    public ServiceResponseException toServiceResponseException(int errorCode){
        return new ServiceResponseException(errorCode,error,message);
    }

    public ErrorResponse toErrorResponse(){
        return new ErrorResponse(error,message);
    }

}
