package com.lostin.auth.repository;

public interface Cache {
    void put(String key, Object value, long ttl);
    Object get(String key);
    void delete(String key);
}
