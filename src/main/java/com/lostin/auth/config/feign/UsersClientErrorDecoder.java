package com.lostin.auth.config.feign;

import com.lostin.auth.exception.*;
import com.lostin.auth.request_response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
public class UsersClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        log.debug("Method: {} Response: {}",methodKey,response);
        ErrorResponse errorResponse;
        try (InputStream is = response.body().asInputStream()){
            errorResponse = objectMapper.readValue(is, ErrorResponse.class);
        } catch (IOException e) {
            log.error("Error decoding response",e);
            throw new ServerError();
        }
        switch (response.status()){
            case 400 -> {
                return ServerError.from(errorResponse, InternalBadRequestException.class);
            }
            case 401 -> {
                log.error("Unauthorized access to Users Service: Invalid microservice token");
                return new ServerError();
            }
            case 403 -> {
                log.error("Forbidden access to Users Service: AuthService doesn't have permission to access this resource");
                return new ServerError();
            }
            case 404 -> {
                return ServerError.from(errorResponse, NotFoundException.class);
            }
            case 409 -> {
                return ServerError.from(errorResponse, ConflictException.class);
            }
            default -> {
                log.error("Error {} from Users Service",response.status());
                return new ServerError();
            }
        }
    }
}
