package com.lostin.auth.jwt.jwk.core;

import com.lostin.auth.exception.ServerError;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class NimbusRsaJwk implements RsaJwk{
    private RSAKey key;

    @Override
    public String getPublicKey() {
        try {
            return key.toPublicKey().getFormat();
        }catch (JOSEException e){
            log.error("Error converting RSA key to public key",e);
            throw new ServerError();
        }
    }

    @Override
    public RSAPublicKey getPublicKeyObject() {
        try {
            return key.toRSAPublicKey();
        }catch (JOSEException e){
            log.error("Error converting RSA key to public key",e);
            throw new ServerError();
        }
    }

    @Override
    public String getPrivateKey() {
        try {
            return key.toPrivateKey().getFormat();
        }catch (JOSEException e){
            log.error("Error converting RSA key to private key",e);
            throw new ServerError();
        }
    }

    @Override
    public RSAPrivateKey getPrivateKeyObject() {
        try {
            return key.toRSAPrivateKey();
        } catch (JOSEException e) {
            log.error("Error converting RSA key to private key",e);
            throw new ServerError();
        }
    }

    @Override
    public String getKeyId() {
        return key.getKeyID();
    }

    @Override
    public String getAlgorithm() {
        return key.getAlgorithm().getName();
    }

    public Map<String,Object> toJsonMap(){
        return key.toJSONObject();
    }
}
