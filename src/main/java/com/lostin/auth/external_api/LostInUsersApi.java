package com.lostin.auth.external_api;

import com.lostin.auth.model.core.user.UserId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LostInUsersApi {

    //todo
    public UserId createUser(String email, String username){
        return UserId.validated(UUID.randomUUID());
    }

    //todo
    public UserId findUserByEmail(String email){
        return UserId.validated(UUID.randomUUID());
    }


}
