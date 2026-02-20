package com.lostin.auth.model.proxy;

import com.lostin.auth.model.core.user.UserId;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class UserCredentialsProxy {
    private final UserId userId;
    private final String passwordHash;

    public UUID getId() {
        if(userId == null){
            return null;
        }
        return userId.value();
    }
}
