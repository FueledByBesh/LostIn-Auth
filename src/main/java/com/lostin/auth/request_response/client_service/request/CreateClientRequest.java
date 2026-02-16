package com.lostin.auth.request_response.client_service.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lostin.auth.model.core.client.ClientType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateClientRequest(
        @NotNull @NotEmpty
        Set<String> redirectUris,
        @NotNull
        ClientType type,
        Boolean requirePkce,
        Boolean trusted,
        @NotNull @NotEmpty
        Set<String> allowedScopes,
        @NotNull
        UUID userId,
        @NotBlank
        String name,
        String description,
        String logoUri
) {
}
