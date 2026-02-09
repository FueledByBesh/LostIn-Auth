package com.lostin.auth.request_response.client_service.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public class CreateClientResponse {
    final UUID clientId;
    final String clientSecret;
}
