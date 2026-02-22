package com.lostin.auth.client_api.users.request;

public record CreateUserRequest(
        String email,
        String username
) {
}
