package com.lostin.auth.jwt.jwk.core;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface RsaJwk {
    String getPublicKey();
    RSAPublicKey getPublicKeyObject();
    String getPrivateKey();
    RSAPrivateKey getPrivateKeyObject();
    String getKeyId();
    String getAlgorithm();
}
