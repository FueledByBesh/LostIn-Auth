package com.lostin.auth.repository.impl.client;

import com.lostin.auth.model.entity.ClientEntity;
import com.lostin.auth.model.mapper.ClientMapper;
import com.lostin.auth.model.proxy.ClientProxy;
import com.lostin.auth.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
@Qualifier("jpa-impl")
public class ClientRepoJpaImpl implements ClientRepository {

    private final ClientJpaRepo clientJpaRepo;
    private final ClientMapper mapper;

    @Override
    public Optional<ClientProxy> findClientById(UUID clientId) {
        return clientJpaRepo.findById(clientId).map(mapper::toProxy);
    }

    @Override
    public Optional<List<String>> findClientURIsById(UUID clientId) {
        return Optional.empty();
    }

    @Override
    public ClientProxy save(ClientProxy client) {
        ClientEntity entity = clientJpaRepo.save(mapper.toEntity(client));
        return mapper.toProxy(entity);
    }

}
