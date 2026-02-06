package com.lostin.auth.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
public class SSOSessionEntity {

    @Id
    private String sessionId;
    private UUID userId;
    private Instant expireTime;
    private Boolean revoked;

}
