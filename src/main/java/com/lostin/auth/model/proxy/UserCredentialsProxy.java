package com.lostin.auth.model.proxy;

import com.lostin.auth.model.core.user.UserId;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCredentialsProxy {
    private final UserId userId;
    private final String passwordHash;
}
