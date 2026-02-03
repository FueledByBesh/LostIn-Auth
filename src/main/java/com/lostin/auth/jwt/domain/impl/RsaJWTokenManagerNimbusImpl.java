package com.lostin.auth.jwt.domain.impl;

import com.lostin.auth.exception.ServerError;
import com.lostin.auth.jwt.core.JWToken;
import com.lostin.auth.jwt.core.JWTokenMetadata;
import com.lostin.auth.jwt.domain.RsaJWTokenManager;
import com.lostin.auth.jwt.exception.InvalidTokenException;
import com.lostin.auth.jwt.jwk.core.RsaJwk;
import com.lostin.auth.jwt.jwk.manager.RsaJWKeyManager;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Component
@Qualifier("nimbus-impl")
@RequiredArgsConstructor
public class RsaJWTokenManagerNimbusImpl implements RsaJWTokenManager {

    private final RsaJWKeyManager keyManager;

    @Override
    public JWToken generateAndSign(JWTokenMetadata metadata) {
        RsaJwk activeKey;
        Optional<RsaJwk> optionalActiveKey = keyManager.getActiveKey();
        if (optionalActiveKey.isEmpty()) {
            log.error("There is no active key, generating new one!");
            activeKey = keyManager.generateNewKey();
        } else {
            activeKey = optionalActiveKey.get();
        }

        Instant now = Instant.now();
        Instant expTime = now.plus(metadata.getExpiresAfter(), ChronoUnit.SECONDS);

        JWTClaimsSet.Builder claimBuilder = new JWTClaimsSet.Builder()
                .expirationTime(Date.from(expTime));


        Map<String, Object> claims = metadata.getClaimMap();
        if(claims ==null || claims.isEmpty()) {
            log.error("Claims map is empty!");
            throw new ServerError();
        }
        claims.forEach(claimBuilder::claim);

        JWTClaimsSet claimsSet = claimBuilder.build();
        JWSHeader header = new JWSHeader.Builder(algorithmMapper(activeKey.getAlgorithm()))
                .keyID(activeKey.getKeyId())
                .build();

        return signToken(header, claimsSet,activeKey.getPrivateKeyObject());
    }

    private JWToken signToken(JWSHeader header, JWTClaimsSet claimsSet, RSAPrivateKey privateKey){
        RSASSASigner signer = new RSASSASigner(privateKey);
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        try {
            signedJWT.sign(signer);
        } catch (Exception e) {
            log.error("Error signing token",e);
            throw new ServerError();
        }
        return new JWToken(signedJWT.serialize());
    }


    @Override
    public boolean validateToken(JWToken token) throws InvalidTokenException {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token.value());
        } catch (ParseException e) {
            log.debug("Error parsing token",e);
            throw new InvalidTokenException("Invalid token format");
        }

        JWSHeader header = signedJWT.getHeader();
        String keyId = header.getKeyID();
        Optional<RsaJwk> optionalKey = keyManager.getKeyById(keyId);
        if(optionalKey.isEmpty()){
            log.debug("Key not found for key id: {}",keyId);
            throw new InvalidTokenException("Key not found!");
        }
        RSASSAVerifier verifier = new RSASSAVerifier(optionalKey.get().getPublicKeyObject());
        try {
            return signedJWT.verify(verifier);
        }catch (JOSEException e) {
            log.warn("Smth Strange happened: Error verifying token",e);
            throw new InvalidTokenException("Error verifying token");
        }
    }

    private JWSAlgorithm algorithmMapper(String algorithm) {
        return switch (algorithm) {
            case "RS256" -> JWSAlgorithm.RS256;
            case "RS384" -> JWSAlgorithm.RS384;
            case "RS512" -> JWSAlgorithm.RS512;
            default -> {
                log.error("Unsupported algorithm: {}", algorithm);
                throw new ServerError();
            }
        };
    }

}
