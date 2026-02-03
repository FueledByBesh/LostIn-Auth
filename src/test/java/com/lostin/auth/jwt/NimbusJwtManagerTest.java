package com.lostin.auth.jwt;

import com.lostin.auth.jwt.core.JWToken;
import com.lostin.auth.jwt.core.JWTokenMetadata;
import com.lostin.auth.jwt.domain.impl.RsaJWTokenManagerNimbusImpl;
import com.lostin.auth.jwt.jwk.gen.NimbusRSAKeyGenerator;
import com.lostin.auth.jwt.jwk.manager.TempNimbusRsaJWKeyManager;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

public class NimbusJwtManagerTest {

    final NimbusRSAKeyGenerator keyGenerator = new NimbusRSAKeyGenerator();
    final String fileName = "nimbusgen-jwk-rsa.json";
    final TempNimbusRsaJWKeyManager keyManager = new TempNimbusRsaJWKeyManager(fileName,keyGenerator,new ObjectMapper());
    final RsaJWTokenManagerNimbusImpl tokenManager = new RsaJWTokenManagerNimbusImpl(keyManager);

    @Test
    void generateToken(){
//        keyManager.init();
//
//        JWTokenMetadata metadata = JWTokenMetadata.
//                .tokenId("test-token-id")
//                .subject("test-subject")
//                .issuer("test-issuer")
//                .audience("test-audience")
//                .expiresAfter(10000L)
//                .build();
//        JWToken token = tokenManager.generateAndSign(metadata);
//        assert token != null;
//        System.out.println(token.value());
    }

    @Test
    void validateToken(){
        String token = "eyJraWQiOiIyMDI2LTAxLTMwVDE5OjQ3OjI2LjQyNTkwM1oiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0ZXN0LWlzc3VlciIsInN1YiI6InRlc3Qtc3ViamVjdCIsImF1ZCI6InRlc3QtYXVkaWVuY2UiLCJleHAiOjE3Njk5MDIxMjgsImlhdCI6MTc2OTg5MjEyOCwianRpIjoidGVzdC10b2tlbi1pZCJ9.IKr9dgZsGt4FyV6IHPk_gzXwCV7SC08HvPVZTd8imJFl32LXXx6lSOIwFIYu_3JVzqkM4CvAq3Qg5-_rs7zj5EBNVyTaXOuaHJnRwUqdCXqFWlWzVRNnIWmfSdKeH8XAE-MQl41-ZJg1A8JNATPx2rn4hz4gMe2gE9J1SiG4Ce2ObURTWRG3FcTB9Z-H73KswKqb3KsEv-vCi6kxsrSVdp1ArVS1d9GMrPbVwwDrNJ5-7LQXcDNwraxHcpSe3hIB8Y_ocpSbTqrvKZSKtyrnkMaWd1eLZVnjABojJ1snvSoqLaM2U6AICnqoH1TPxsDynKogNK0JUcLJ5N4cqPdIbQ";
        keyManager.init();
        boolean valid = tokenManager.validateToken(JWToken.from(token));
        System.out.println(valid);
    }


}
