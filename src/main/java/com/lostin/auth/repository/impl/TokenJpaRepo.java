package com.lostin.auth.repository.impl;

import com.lostin.auth.model.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenJpaRepo extends JpaRepository<TokenEntity, UUID> {
}
