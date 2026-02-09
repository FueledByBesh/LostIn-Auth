package com.lostin.auth.repository;

import java.util.Optional;

public interface Cache {
    void put(String key, String value, long ttl);
    Optional<String> get(String key);
    void delete(String key);
}
