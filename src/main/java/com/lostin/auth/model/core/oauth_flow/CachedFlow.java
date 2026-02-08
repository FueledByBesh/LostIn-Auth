package com.lostin.auth.model.core.oauth_flow;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CachedFlow {
    private final UUID flowId;
    private final CachedFlowClient client;
    private CachedFlowUser user;
    private String authCode;
}
