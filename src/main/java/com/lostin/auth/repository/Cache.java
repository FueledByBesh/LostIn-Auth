package com.lostin.auth.repository;

import com.lostin.auth.repository.impl.cache.CachingOption;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface Cache {
    void put(@NonNull CachingOption option, @NonNull String key,@NonNull String value, long ttl,@NonNull TimeUnit unit);
    Optional<String> get(@NonNull CachingOption option,@NonNull String key);
    void delete(@NonNull CachingOption option, @NonNull String key);
}
