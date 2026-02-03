package com.lostin.auth.util;


import java.security.SecureRandom;
import java.util.Base64;

public class OpaqueTokenGenerator {

    public static String generateOpaqueToken(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
