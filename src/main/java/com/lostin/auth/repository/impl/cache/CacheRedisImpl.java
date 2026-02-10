package com.lostin.auth.repository.impl.cache;

import com.lostin.auth.repository.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CacheRedisImpl implements Cache {

    //todo
//    private final RedisTemplate

    @Override
    public void put(CachingOption option,String key, String value, long ttl) {
        String redisKey = option.getKey(key);
    }

    @Override
    public Optional<String> get(CachingOption option,String key) {
        return Optional.empty();
    }

    @Override
    public void delete(CachingOption option,String key) {

    }
}
