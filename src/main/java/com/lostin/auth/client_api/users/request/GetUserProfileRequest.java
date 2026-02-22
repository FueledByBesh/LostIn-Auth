package com.lostin.auth.client_api.users.request;

import java.util.UUID;

public record GetUserProfileRequest(
        UUID userId
) {
}
