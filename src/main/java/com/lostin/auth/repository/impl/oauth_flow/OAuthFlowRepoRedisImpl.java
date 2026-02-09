package com.lostin.auth.repository.impl.oauth_flow;

import com.lostin.auth.model.core.oauth_flow.CachedFlow;
import com.lostin.auth.model.core.oauth_flow.CachedFlowClient;
import com.lostin.auth.repository.OAuthFlowRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Qualifier("redis-impl")
public class OAuthFlowRepoRedisImpl implements OAuthFlowRepository {


    /**
     * creates new flow in cache
     * @param flowClient cached flow client object
     * @return unique flow id of type {@link UUID}
     */
    @Override
    public CachedFlow persistFlow(CachedFlowClient flowClient) {
        UUID flowId;
        do{
            flowId = UUID.randomUUID();
        }while (isFlowPresent(flowId));
        CachedFlow flow = CachedFlow.builder()
                .client(flowClient)
                .build();
        saveFlow(flow);
        return flow;
    }

    @Override
    public void saveFlow(CachedFlow flow) {
        //todo
    }

    @Override
    public void deleteFlow(UUID flowId) {
        //todo
    }

    @Override
    public Optional<CachedFlow> getCachedFlow(UUID flowId) {
        //todo
        return Optional.empty();
    }

    @Override
    public boolean isFlowPresent(UUID flowId) {
        //todo
        return false;
    }
}
