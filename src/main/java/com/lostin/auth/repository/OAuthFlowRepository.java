package com.lostin.auth.repository;

public interface OAuthFlowRepository {

    void saveFlow(String flowId, String clientId);
    void deleteFlow(String flowId);
    String getClientId(String flowId);
    boolean isFlowPresent(String flowId);
}
