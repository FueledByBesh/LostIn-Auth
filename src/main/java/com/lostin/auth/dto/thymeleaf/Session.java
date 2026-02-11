package com.lostin.auth.dto.thymeleaf;

public record Session (
        String sessionId,
        String userId,
        String userEmail,
        String username,
        String userAvatarUri
){}
