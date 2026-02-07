package com.lostin.auth.repository.impl.oauth_flow;

import com.lostin.auth.model.proxy.CachedFlowClient;
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
     * @param client cached client
     * @return unique flow id of type {@link UUID}
     */
    @Override
    public UUID persistFlow(CachedFlowClient client) {
        UUID flowId;
        do{
            flowId = UUID.randomUUID();
        }while (isFlowPresent(flowId));
        this.saveFlow(flowId,client);
        return flowId;
    }

    @Override
    public void saveFlow(UUID flowId, CachedFlowClient client) {

    }

    @Override
    public void deleteFlow(UUID flowId) {

    }

    @Override
    public Optional<CachedFlowClient> getCachedClient(UUID flowId) {
        return Optional.empty();
    }

    @Override
    public boolean isFlowPresent(UUID flowId) {
        return false;
    }

//    @Override
//    public UUID generateUniqueFlowId() {
//        //todo
//        return UUID.randomUUID();
//    }
}
