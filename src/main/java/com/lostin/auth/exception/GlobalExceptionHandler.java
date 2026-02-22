package com.lostin.auth.exception;

import com.lostin.auth.jwt.exception.InvalidTokenException;
import com.lostin.auth.request_response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceResponseException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleServerException(ServiceResponseException e) {
        return ResponseEntity.status(e.statusCode).body(e.toErrorResponse());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleInvalidTokenException(InvalidTokenException e) {
        return ResponseEntity.status(401).body(e.toErrorResponse());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().body(e.toErrorResponse());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleUnAuthorizedException(UnAuthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.toErrorResponse());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toErrorResponse());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleConflictException(ConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.toErrorResponse());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.debug("Validation error", e);
        return ResponseEntity.badRequest().body(new ErrorResponse("VALIDATION_ERROR", "Invalid inputs!"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.debug("Validation error", e);
        return ResponseEntity.badRequest().body(new ErrorResponse("VALIDATION_ERROR", "Invalid inputs!"));
    }

    @ExceptionHandler(ServerError.class)
    public ResponseEntity<@NonNull ErrorResponse> handleServerError(ServerError e) {
        log.error("Internal Server Error", e);
        return ResponseEntity.status(500).build();
    }

    @ExceptionHandler(InternalBadRequestException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleBadRequestException(InternalBadRequestException e) {
        log.error(
                "UNEXPECTED ERROR! Assumptions: " +
                        "1) Auth server parameter validation do not satisfy to User server validation" +
                        "2) Used wrong User server endpoint",
                e);
        return this.handleServerError(e);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("Method not supported", e);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorResponse("METHOD_NOT_ALLOWED", "Method not supported"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NonNull ErrorResponse> handleException(Exception e) {
        log.error("UNEXPECTED SERVER ERROR", e);
        return ResponseEntity.internalServerError().body(
                new ErrorResponse("UNEXPECTED SERVER ERROR", "Smth get wrong")
        );
    }

}
