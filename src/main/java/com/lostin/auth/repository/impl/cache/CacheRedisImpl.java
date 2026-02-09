package com.lostin.auth.repository.impl.cache;

import com.lostin.auth.repository.Cache;
import org.springframework.stereotype.Repository;

@Repository
public class CacheRedisImpl implements Cache {


    @Override
    public void put(String key, Object value, long ttl) {

    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public void delete(String key) {

    }
}
