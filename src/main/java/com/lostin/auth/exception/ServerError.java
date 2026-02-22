package com.lostin.auth.exception;

import com.lostin.auth.request_response.ErrorResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

@Slf4j
@Getter
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

    public static <T extends ServerError> T from(ErrorResponse errorResponse, Class<T> clazz){
        try {
            if(Objects.isNull(errorResponse)){
                return clazz.getDeclaredConstructor().newInstance();
            }
            return clazz.getDeclaredConstructor(String.class,String.class).newInstance(errorResponse.error(),errorResponse.message());
        } catch (Exception e) {
            log.error("Error creating exception from error response",e);
            throw new ServerError();
        }
    }
}
