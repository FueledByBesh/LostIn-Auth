package com.lostin.auth.repository.impl.client;

import com.lostin.auth.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientJpaRepo extends JpaRepository<ClientEntity, UUID> {
}
