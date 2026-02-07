package com.lostin.auth.repository;

import com.lostin.auth.model.proxy.CachedFlowClient;

import java.util.Optional;
import java.util.UUID;

public interface OAuthFlowRepository {

    UUID persistFlow(CachedFlowClient client);
    void saveFlow(UUID flowId, CachedFlowClient client);
    void deleteFlow(UUID flowId);
    Optional<CachedFlowClient> getCachedClient(UUID flowId);
    boolean isFlowPresent(UUID flowId);
//    UUID generateUniqueFlowId();
}
