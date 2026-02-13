package com.lostin.auth.model.core.oauth_flow;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CachedFlowUser {
    private final UUID userId;
}
