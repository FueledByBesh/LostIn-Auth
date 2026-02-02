package com.lostin.auth.jwt.jwk.manager;

import com.lostin.auth.jwt.jwk.core.RsaJwk;

import java.util.List;
import java.util.Optional;

public interface RsaJWKeyManager {

    Optional<RsaJwk> getActiveKey();
    Optional<RsaJwk> getKeyById(String id);
    List<RsaJwk> getAllKeys();
    void saveKeys();
    RsaJwk generateNewKeyWithCustomId(String keyId);
    RsaJwk generateNewKey();

}
