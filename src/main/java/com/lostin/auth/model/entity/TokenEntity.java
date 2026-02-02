package com.lostin.auth.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private UUID tokenId;
    private String value;
    private String subject;
    private String issuer;
    private String audience;
    private Instant issuedAt;
    private Instant expiresAt;

}
