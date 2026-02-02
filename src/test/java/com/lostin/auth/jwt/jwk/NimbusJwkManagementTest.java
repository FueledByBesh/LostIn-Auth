package com.lostin.auth.jwt.jwk;

import com.lostin.auth.jwt.jwk.core.NimbusRsaJwk;
import com.lostin.auth.jwt.jwk.core.RsaJwk;
import com.lostin.auth.jwt.jwk.gen.NimbusRSAKeyGenerator;
import com.lostin.auth.jwt.jwk.manager.TempNimbusRsaJWKeyManager;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

public class NimbusJwkManagementTest {

    final NimbusRSAKeyGenerator keyGenerator = new NimbusRSAKeyGenerator();
    final String fileName = "nimbusgen-jwk-rsa.json";
    final TempNimbusRsaJWKeyManager keyManager = new TempNimbusRsaJWKeyManager(fileName,keyGenerator,new ObjectMapper());

    @Test
    void generateKeys(){
        String keyId = "test-key-id-1";
        NimbusRsaJwk keys = keyGenerator.generateRSAKey(keyId);
        assert keys != null;
        System.out.println(keys.getPublicKey());
        System.out.println(keys.getPrivateKey());
        System.out.println(keys.getKeyId());
    }

    @Test
    void generateKeyFromManager(){
        RsaJwk key = keyManager.generateNewKey();
        assert Objects.nonNull(key);
        System.out.println(key.getKeyId());
    }

    @Test
    void initManager(){
        keyManager.init();
        List<RsaJwk> keys = keyManager.getAllKeys();
        keys.forEach(k-> System.out.println(k.getKeyId()));
    }

    @Test
    void generateAndSaveKeys(){
        keyManager.init();
        keyManager.generateNewKey();
        keyManager.generateNewKey();
        List<RsaJwk> allKeys = keyManager.getAllKeys();
        System.out.println(allKeys.size());
        for (RsaJwk jwk: allKeys)
            System.out.println(jwk.getKeyId());
        keyManager.saveKeys();
    }


}
