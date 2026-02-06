package com.lostin.auth.model.entity;

import com.lostin.auth.model.core.client.ClientStatus;
import com.lostin.auth.model.core.client.ClientType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


/*
 * OAuth Client apps entity
 */

/**
 * Recommended to add in next versions:
 * - Client secret rotation: Expire time for client secret (it shouldn't be live forever)
 * - Policy URI: for consent screen
 * - Dev contacts
 *
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    @Column(name = "id", updatable = false)
    private UUID id; ///client id
    private String secretHash; ///client secret hash
    @Column(name = "redirect_uri", nullable = false)
    private String redirectUri; /// client app's possible (allowed) to redirect URIs.
    @Enumerated(EnumType.STRING)
    private ClientType type;
    @Column(name = "require_pkce")
    private Boolean requirePkce = false;
    @Enumerated(EnumType.STRING)
    private ClientStatus status;
    private Boolean trusted= false;

    /**
     * User account from where client app is created.
     * It's changeable, users can transfer app to another client (max one user can hold one client app at a time)
     */
    @Column(name = "user_id",nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String name; /// client app's name
    private String description; /// client app's description
    @Column(name = "logo_uri")
    private String logoUri; /// client app's logo uri
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @PrePersist
    private void prePersist(){
        createdAt = Instant.now();
    }
}

