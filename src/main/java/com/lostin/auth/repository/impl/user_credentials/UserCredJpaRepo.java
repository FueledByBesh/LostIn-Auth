package com.lostin.auth.repository.impl.user_credentials;

import com.lostin.auth.model.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCredJpaRepo extends JpaRepository<UserCredentialsEntity, UUID> {
}
