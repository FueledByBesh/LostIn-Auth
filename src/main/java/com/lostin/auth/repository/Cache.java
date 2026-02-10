package com.lostin.auth.repository;

import com.lostin.auth.repository.impl.cache.CachingOption;

import java.util.Optional;

public interface Cache {
    void put(CachingOption option, String key, String value, long ttl);
    Optional<String> get(CachingOption option,String key);
    void delete(CachingOption option,String key);
}
