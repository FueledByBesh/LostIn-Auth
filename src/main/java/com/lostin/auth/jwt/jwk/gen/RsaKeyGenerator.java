package com.lostin.auth.jwt.jwk.gen;

import com.lostin.auth.jwt.jwk.core.RsaJwk;
import org.springframework.stereotype.Component;

public interface RsaKeyGenerator {

    //Basic RSA Key generation with key size 2048
    RsaJwk generateRSAKey(String keyId);

    RsaJwk generateRSAKey(String keyId,int keySize);
}
