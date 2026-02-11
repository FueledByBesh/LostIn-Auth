package com.lostin.auth.dto.thymeleaf;

public record Client(
        String appName,
        String description,
        String logoUri
) {
}
