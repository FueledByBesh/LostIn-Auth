package com.lostin.auth.repository;

import com.lostin.auth.model.proxy.TokenProxy;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository {

    Optional<TokenProxy> findTokenById(UUID tokenId);
    Optional<TokenProxy> findTokenByValueHash(String value);
    TokenProxy saveToken(TokenProxy token);
    void deleteToken(UUID tokenId);

}
