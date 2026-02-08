package com.lostin.auth.repository;

import com.lostin.auth.model.core.oauth_flow.CachedFlow;
import com.lostin.auth.model.core.oauth_flow.CachedFlowClient;

import java.util.Optional;
import java.util.UUID;

public interface OAuthFlowRepository {

    CachedFlow persistFlow(CachedFlowClient client);
    void saveFlow(CachedFlow flow);
    void deleteFlow(UUID flowId);
    Optional<CachedFlow> getCachedFlow(UUID flowId);
    boolean isFlowPresent(UUID flowId);
}
