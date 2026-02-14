package com.lostin.auth.repository.impl.cache;

import com.lostin.auth.repository.Cache;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class CacheRedisImpl implements Cache {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void put(@NonNull CachingOption option, @NonNull String key, @NonNull String value,long ttl, @NonNull TimeUnit unit) {
        String redisKey = option.getKey(key);
        redisTemplate.opsForValue().set(redisKey,value,ttl, unit);
    }

    @Override
    public Optional<String> get(@NonNull CachingOption option, @NonNull String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(option.getKey(key)));
    }

    @Override
    public void delete(@NonNull CachingOption option,@NonNull String key) {
        redisTemplate.delete(option.getKey(key));
    }
}
