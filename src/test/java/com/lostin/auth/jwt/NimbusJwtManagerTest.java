package com.lostin.auth.jwt;

import com.lostin.auth.jwt.core.JWToken;
import com.lostin.auth.jwt.core.custom_metadata.ClientJwtMetadata;
import com.lostin.auth.jwt.domain.impl.RsaJWTokenManagerNimbusImpl;
import com.lostin.auth.jwt.exception.InvalidTokenException;
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
        keyManager.init();

        ClientJwtMetadata metadata = ClientJwtMetadata.builder()
                .tokenId("test-token-id")
                .subject("test-subject")
                .issuer("test-issuer")
                .audience("test-audience")
                .expiresAfter(60L) // 1 minute
                .build();

        JWToken token = tokenManager.generateAndSign(metadata);
        assert token != null;
        System.out.println(token.value());
    }

    @Test
    void validateToken(){
        String token = "eyJraWQiOiIyMDI2LTAxLTMwVDE5OjQ3OjI2LjQyNTkwM1oiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0ZXN0LWlzc3VlciIsImF1ZCI6InRlc3QtYXVkaWVuY2UiLCJzdWIiOiJ0ZXN0LXN1YmplY3QiLCJleHAiOjE3NzAxMjU2NzEsImp0aSI6InRlc3QtdG9rZW4taWQifQ.eVs6VYLpbeWrRrzEDQ3ge0eH7hfPhZupMTC6MMVjrzThzk1RTZj-Nv2SLOrZb7cMG-ZZwT_Ifz518YegNo9gCTGfqNtYKDTMI3grB1r8yrftIxLLgdRObBQA6Ycd6uKFeMm5n82NUzuZhJDvQ165RtSUMNFV_FdbWA6aD3LA3IPxQsBNim9NaTdYn1_mGyNPnW8rNgG91MwHFlOn7k5PZQKiAue4FXisKnpiBcwHSYsin_hzsJgmTeLrZlmuayewKDXPcKGrxxDpg-rl3RrSLD0WT6BrbvQcdixsanZo8HKeEWjDus83MGsOY0MnPr7xhVfqQUW_gi-m56gswvreaQ";
//        String token1 = "eyJraWQiOiIyMDI2LTAxLTMwVDE5OjQ3OjI2LjQyNTkwM1oiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0ZXN0LWlzc3VlciIsImF1ZCI6InRlc3QtYXVkaWVuY2UiLCJzdWIiOiJ0ZXN0LXN1YmplY3QiLCJleHAiOjE3NzAwOTI1NjcsImp0aSI6InRlc3QtdG9rZW4taWQifQ.EkhEL0NpPB2vS8_5SfqscgNk_3LmANXoyKhAYlIl5EOT4eGGB06-llywo-91l2lAkvr73PuB8MVbXFICkPotfFeuWnsGyPiJyQyzOZu5LT280al2d7qtnf_dyI2WMGGcbR6uDsY-Qd_XJwHfKdccWpdwjZW-Fwk_cfonIVNJjKMB3As4fvA74nQ_6HIs1IA_vVqTym4_SgS1dINTBUhC5fGUyXchTf-vDb6FJEd66Do1cQhJDCMJhvjCjQSfGb0isXnJGTNy9BBjKy0nh7-PQy_6owjPbnpHwJ6EzZ3MILffFAOWXp-HW2DJzrbctHYfDfoBACiMxhqOse_kp4f_mA";
        keyManager.init();
        boolean valid = true;
        try {
            tokenManager.validateToken(JWToken.from(token));
        }catch (InvalidTokenException e){
            valid = false;
            System.out.println(e.getMessage());
        }
        System.out.println(valid);
    }


}
