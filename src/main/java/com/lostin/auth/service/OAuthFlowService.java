package com.lostin.auth.service;

import com.lostin.auth.exception.NotFoundException;
import com.lostin.auth.model.core.oauth_flow.CachedFlow;
import com.lostin.auth.model.core.oauth_flow.CachedFlowClient;
import com.lostin.auth.model.core.oauth_flow.CachedFlowUser;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.repository.OAuthFlowRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthFlowService {

    private final OAuthFlowRepository flowRepository;

    public void saveUserIntoFlow(@NonNull UUID flowId, UserId userId) throws NotFoundException{
        CachedFlow flow = flowRepository.getCachedFlow(flowId).orElseThrow(
                ()-> new NotFoundException("FLOW_NOT_FOUND","Flow with id: " + flowId + " not found")
        );

        CachedFlowUser user = CachedFlowUser.builder()
                .userId(userId.value())
                .build();
        flow.setUser(user);
        flowRepository.saveFlow(flow);
    }

    public CachedFlowClient getClient(@NonNull UUID flowId) throws NotFoundException{
        return flowRepository.getCachedFlow(flowId).orElseThrow(
                ()-> new NotFoundException("FLOW_NOT_FOUND","Flow with id: " + flowId + " not found")
        ).getClient();
    }


}
