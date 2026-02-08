package com.lostin.auth.external_api;

import com.lostin.auth.model.core.user.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/*
TODO: to use @FeignClient from springframework.cloud to avoid boilerplate code
 */
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


    /* TODO:
        write endpoint in users service that gives multiple users by its IDs
        Needed for SSO sessions (so if there are multiple sessions,
        i don't have to users service multiple times)
        return type should be List of minimal user data (username,email,logo uri)
     */
    public List<Void> findUsersByIds(List<UserId> userIds){
        return null;
    }

}
