package com.lostin.auth.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Session {
    @Id
    private UUID id;

}
