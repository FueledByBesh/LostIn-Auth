package com.lostin.auth.model.core.auth_token;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TokensMetadata(
        UUID tokensId,
        String subject
) {
}
