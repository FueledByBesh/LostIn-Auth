package com.lostin.auth.repository.impl.cache;

import com.lostin.auth.repository.Cache;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CacheRedisImpl implements Cache {


    @Override
    public void put(String key, String value, long ttl) {

    }

    @Override
    public Optional<String> get(String key) {
        return null;
    }

    @Override
    public void delete(String key) {

    }
}
