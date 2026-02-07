package com.lostin.auth.model.proxy;

import com.lostin.auth.model.core.client.ClientStatus;
import com.lostin.auth.model.core.client.ClientType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class ClientProxy {

    private UUID id;
    private String secretHash;
    private Set<String> redirectUris;
    private ClientType type;
    private Boolean requirePkce;
    private Boolean trusted;
    private ClientStatus status;
    private Set<String> allowedScopes;

    private UUID userId;
    private String name;
    private String description;
    private String logoUri;
    private Instant createdAt;
}
