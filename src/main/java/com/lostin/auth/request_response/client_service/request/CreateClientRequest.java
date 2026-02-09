package com.lostin.auth.request_response.client_service.request;

import com.lostin.auth.model.core.client.ClientType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class CreateClientRequest {
    @NotNull @NotEmpty
    final Set<String> redirectUris;
    @NotNull
    final ClientType type;
    final Boolean requirePkce;
    final Boolean trusted;
    @NotNull @NotEmpty
    final Set<String> allowedScopes;
    @NotNull
    final UUID userId;
    @NotBlank
    final String name;
    final String description;
    final String logoUri;
}
