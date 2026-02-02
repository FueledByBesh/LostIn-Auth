package com.lostin.auth.model.proxy;

import java.time.Instant;
import java.util.UUID;

public class TokenProxy {

    private UUID tokenId;
    private String value;
    private String subject;
    private String issuer;
    private String audience;
    private Instant issuedAt;
    private Instant expiresAt;

}
