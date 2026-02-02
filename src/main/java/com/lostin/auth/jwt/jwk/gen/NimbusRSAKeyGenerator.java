package com.lostin.auth.jwt.jwk.gen;

import com.lostin.auth.exception.ServerError;
import com.lostin.auth.jwt.jwk.core.NimbusRsaJwk;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Qualifier("nimbus-impl")
public class NimbusRSAKeyGenerator implements RsaKeyGenerator{

    @Override
    public NimbusRsaJwk generateRSAKey(String keyId) {
        return this.generateRSAKey(keyId,2048);
    }

    @Override
    public NimbusRsaJwk generateRSAKey(String keyId,int keySize) {
        try {
            RSAKey keys = new RSAKeyGenerator(keySize)
                    .keyID(keyId)
                    .algorithm(JWSAlgorithm.RS256)
                    .generate();

            return new NimbusRsaJwk(keys);
        }catch (JOSEException e){
            log.error("Error generating RSA key",e);
            throw new ServerError();
        }
    }
}
