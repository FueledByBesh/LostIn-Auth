package com.lostin.auth.repository;

import com.lostin.auth.model.proxy.ClientProxy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {

    Optional<ClientProxy> findClientById(UUID clientId);
    Optional<List<String>> findClientURIsById(UUID clientId);
    ClientProxy save(ClientProxy client);
}
