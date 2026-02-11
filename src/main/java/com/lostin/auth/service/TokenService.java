package com.lostin.auth.service;

import com.lostin.auth.jwt.core.JWToken;
import com.lostin.auth.jwt.core.custom_metadata.ClientJwtMetadata;
import com.lostin.auth.jwt.domain.RsaJWTokenManager;
import com.lostin.auth.jwt.exception.InvalidTokenException;
import com.lostin.auth.model.core.auth_token.AccessToken;
import com.lostin.auth.model.core.auth_token.RefreshToken;
import com.lostin.auth.model.core.auth_token.Tokens;
import com.lostin.auth.model.proxy.TokenProxy;
import com.lostin.auth.repository.TokenRepository;
import com.lostin.auth.util.Hasher;
import com.lostin.auth.util.OpaqueTokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

/**
 * Token Service, uses JWT as a token
 */
@Service
@Slf4j
public class TokenService {

    private final RsaJWTokenManager tokenManager;
    private final TokenRepository tokenRepository;

    private final Long REFRESH_TOKEN_TTL_HOURS;
    private final Long ACCESS_TOKEN_TTL_SECONDS;

    // Constructor injection
    public TokenService(
            @Qualifier("nimbus-impl") RsaJWTokenManager tokenManager,
            @Qualifier("jpa-impl") TokenRepository tokenRepository,
            @Value( "${AUTH_REFRESH_TOKEN_TTL_HOURS}")
            Long REFRESH_TOKEN_TTL_HOURS,
            @Value( "${AUTH_ACCESS_TOKEN_TTL_SECONDS}")
            Long ACCESS_TOKEN_TTL_SECONDS
    ) {
        this.tokenManager = tokenManager;
        this.tokenRepository = tokenRepository;
        this.REFRESH_TOKEN_TTL_HOURS = REFRESH_TOKEN_TTL_HOURS;
        this.ACCESS_TOKEN_TTL_SECONDS = ACCESS_TOKEN_TTL_SECONDS;
    }


    public Tokens generateClientTokens(UUID userId, String audience,String scopes) {
        String refreshToken = OpaqueTokenGenerator.generateOpaqueToken();
        String refTokenHash = Hasher.sha256(refreshToken);
        Instant now = Instant.now();
        Instant expTime = now.plus(REFRESH_TOKEN_TTL_HOURS, ChronoUnit.HOURS);
        TokenProxy proxy = TokenProxy.builder()
                .valueHash(refTokenHash)
                .subject(userId.toString())
                .issuer("auth-service")
                .audience(audience)
                .issuedAt(now)
                .expiresAt(expTime)
                .scopes(scopes)
                .build();

        proxy = tokenRepository.saveToken(proxy);
        AccessToken accessToken = generateClientAccessToken(proxy);
        return Tokens.from(accessToken, RefreshToken.from(refreshToken));
    }

    public AccessToken refreshClientToken(RefreshToken refreshToken) throws InvalidTokenException{
        String hashedToken = Hasher.sha256(refreshToken.value());
        Optional<TokenProxy> optionalTokenProxy = tokenRepository.findTokenByValueHash(hashedToken);
        if(optionalTokenProxy.isEmpty()){
            throw new InvalidTokenException("Invalid refresh token!");
        }
        TokenProxy proxy = optionalTokenProxy.get();
        return generateClientAccessToken(proxy);
    }

    private AccessToken generateClientAccessToken(TokenProxy proxy){
        ClientJwtMetadata metadata = ClientJwtMetadata.builder()
                .tokenId(proxy.getValueHash())
                .subject(proxy.getSubject())
                .issuer(proxy.getIssuer())
                .audience(proxy.getAudience())
                .expiresAfter(ACCESS_TOKEN_TTL_SECONDS)
                .scopes(proxy.getScopes())
                .build();
        JWToken jwt = tokenManager.generateAndSign(metadata);
        return AccessToken.from(jwt);
    }


}
