package com.lostin.auth.repository.impl.user_credentials;

import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.model.mapper.UserCredentialsMapper;
import com.lostin.auth.model.proxy.UserCredentialsProxy;
import com.lostin.auth.repository.UserCredentialsRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Qualifier("jpa-impl")
public class UserCredRepoJpaImpl implements UserCredentialsRepository {

    private final UserCredJpaRepo jpaRepo;
    private final UserCredentialsMapper mapper;
    private final EntityManager entityManager;

    @Override
    public Optional<UserCredentialsProxy> findCredentialsByUserId(UserId userId) {
        return jpaRepo.findById(userId.value()).map(mapper::toProxy);
    }

    @Override
    public UserCredentialsProxy saveCredentials(UserCredentialsProxy credentials) {
        return mapper.toProxy(jpaRepo.save(mapper.toEntity(credentials)));
    }

    @Override
    public void deleteCredentials(UserId userId) {
        jpaRepo.deleteById(userId.value());
    }
}
