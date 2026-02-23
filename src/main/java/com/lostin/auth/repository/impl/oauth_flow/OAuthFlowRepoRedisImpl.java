package com.lostin.auth.repository.impl.oauth_flow;

import com.lostin.auth.model.core.oauth_flow.CachedFlow;
import com.lostin.auth.model.core.oauth_flow.CachedFlowClient;
import com.lostin.auth.repository.OAuthFlowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
@Qualifier("redis-impl")
public class OAuthFlowRepoRedisImpl implements OAuthFlowRepository {

    private final RedisTemplate<String,CachedFlow> redisTemplate;
    private final String FLOW_PREFIX = "flow:";

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
                .flowId(flowId)
                .client(flowClient)
                .build();
        saveFlow(flow);
        return flow;
    }

    @Override
    public void saveFlow(CachedFlow flow) {
        redisTemplate.opsForValue().set(FLOW_PREFIX+flow.getFlowId(),flow, 10, TimeUnit.MINUTES);
    }

    @Override
    public void deleteFlow(UUID flowId) {
        redisTemplate.delete(FLOW_PREFIX+flowId);
    }

    @Override
    public Optional<CachedFlow> getCachedFlow(UUID flowId) {
        CachedFlow flow = redisTemplate.opsForValue().get(FLOW_PREFIX + flowId);
        return Optional.ofNullable(flow);
    }

    @Override
    public boolean isFlowPresent(UUID flowId) {
        System.out.println(flowId);
        return redisTemplate.hasKey(FLOW_PREFIX+flowId);
    }

    @Override
    public void saveAuthCode(String code, UUID flowId) {
//        todo
    }

    @Override
    public UUID getFlowIdByAuthCode(String code) {
//        todo
        return null;
    }
}
