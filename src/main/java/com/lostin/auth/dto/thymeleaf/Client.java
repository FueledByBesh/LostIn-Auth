package com.lostin.auth.dto.thymeleaf;

import lombok.Builder;

@Builder
public record Client(
        String appName,
        String description,
        String logoUri
) {
}
