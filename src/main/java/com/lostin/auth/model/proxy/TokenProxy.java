package com.lostin.auth.model.proxy;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;


@Data
@Builder
public class TokenProxy {

    private UUID id;
    private String value;
    private String subject;
    private String issuer;
    private String audience;
    private Instant issuedAt;
    private Instant expiresAt;
    private String scopes;
    private Boolean revoked;
}
