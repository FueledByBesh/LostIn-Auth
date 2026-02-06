package com.lostin.auth.repository.impl.token;

import com.lostin.auth.model.entity.TokenEntity;
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
        return tokenJpaRepo.findById(tokenId).map(mapper::toProxy);
    }

    @Override
    public Optional<TokenProxy> findTokenByValueHash(String value) {
        return tokenJpaRepo.findByValue(value).map(mapper::toProxy);
    }

    @Override
    public TokenProxy saveToken(TokenProxy token) {
        TokenEntity entity = mapper.toEntity(token);
        return mapper.toProxy(tokenJpaRepo.save(entity));
    }

    @Override
    public void deleteToken(UUID tokenId) {
        tokenJpaRepo.deleteById(tokenId);
    }
}
