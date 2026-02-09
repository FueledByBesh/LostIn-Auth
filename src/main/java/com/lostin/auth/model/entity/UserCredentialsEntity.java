package com.lostin.auth.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "user_credentials")
public class UserCredentialsEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "password_hash",nullable = false)
    private String passwordHash;

//    private Boolean twoFactorEnabled = false;

}
