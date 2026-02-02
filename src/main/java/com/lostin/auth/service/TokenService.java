package com.lostin.auth.service;

import com.lostin.auth.jwt.domain.RsaJWTokenManager;
import com.lostin.auth.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Token Service, uses JWT as token
 */
@Service
@Slf4j
public class TokenService {

    private final RsaJWTokenManager tokenManager;
    private final TokenRepository tokenRepository;


    // Constructor injection
    public TokenService(
            @Qualifier("nimbus-impl") RsaJWTokenManager tokenManager,
            @Qualifier("jpa-impl") TokenRepository tokenRepository
    ) {
        this.tokenManager = tokenManager;
        this.tokenRepository = tokenRepository;
    }





}
