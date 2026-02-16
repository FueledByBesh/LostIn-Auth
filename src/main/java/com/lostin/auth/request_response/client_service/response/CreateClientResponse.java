package com.lostin.auth.request_response.client_service.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateClientResponse (
        UUID clientId,
        String clientSecret
) {}
