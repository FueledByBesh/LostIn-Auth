package com.lostin.auth.util;

import com.lostin.auth.exception.ServerError;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Slf4j
public class Hasher {

    /**
     * Hashes String using the SHA-256 algorithm and returns hex formatted String
     * @param plain String to be hashed
     * @return Hashed String in hex format
     */
    public static String sha256(@NonNull String plain){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(plain.getBytes());
            return HexFormat.of().formatHex(hash);
        }catch (NoSuchAlgorithmException e){
            log.error("Error hashing password: No such algorithm",e);
            throw new ServerError();
        }
    }

}
