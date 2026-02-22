package com.lostin.auth.dto.users_client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lostin.auth.util.validation.annotation.ValidEmail;
import com.lostin.auth.util.validation.annotation.ValidUUID;
import com.lostin.auth.util.validation.annotation.ValidUsername;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserMinimalProfile (
        @ValidUUID
        UUID userId,
        @ValidEmail
        String email,
        @ValidUsername
        String username,
        String avatarUri
){}
