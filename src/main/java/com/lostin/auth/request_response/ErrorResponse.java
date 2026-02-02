package com.lostin.auth.request_response;

public record ErrorResponse(
        String error, String message
) {
}
