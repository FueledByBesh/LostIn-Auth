package com.lostin.auth.client_api.users.request;

import java.util.List;
import java.util.UUID;

public record GetUserProfilesRequest(
        List<UUID> userIds
) {
}
