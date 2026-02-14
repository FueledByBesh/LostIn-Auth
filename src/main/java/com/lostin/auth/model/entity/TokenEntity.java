package com.lostin.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


/**
 * Token entity that represents Refresh Tokens in the Database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tokens")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String value;
    @Column(nullable = false)
    private String subject;
    private String issuer;
    private String audience; // clientId
    @Column(name = "issued_at")
    private Instant issuedAt;
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;
    @Column(name = "scopes",length = 1024)
    private String scopes;

    @Builder.Default
    private Boolean revoked = false;
}
