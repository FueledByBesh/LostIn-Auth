package com.lostin.auth.repository.impl;

import com.lostin.auth.model.mapper.TokenMapper;
import com.lostin.auth.model.proxy.TokenProxy;
import com.lostin.auth.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
@Qualifier("jpa-impl")
public class TokenRepoJpaImpl implements TokenRepository {

    private final TokenJpaRepo tokenJpaRepo;
    private final TokenMapper mapper;

    @Override
    public Optional<TokenProxy> findTokenById(UUID tokenId) {
        return Optional.empty();
    }

    @Override
    public Optional<TokenProxy> findTokenByValue(String value) {
        return Optional.empty();
    }

    @Override
    public TokenProxy saveToken(TokenProxy token) {
        return null;
    }

    @Override
    public void deleteToken(UUID tokenId) {

    }
}
