package com.lostin.auth.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    private UUID userId;

    @NotNull
    private String passwordHash;

}
