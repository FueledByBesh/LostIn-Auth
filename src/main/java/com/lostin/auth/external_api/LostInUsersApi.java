package com.lostin.auth.external_api;

import com.lostin.auth.exception.AlreadyExistException;
import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.model.core.user.Username;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
TODO: to use @FeignClient from springframework.cloud to avoid boilerplate code
 */
@Component
public class LostInUsersApi {

    //todo
    public UserId createUser(Email email, Username username) throws AlreadyExistException, BadRequestException {
        return new UserId(UUID.randomUUID());
    }

    //todo
    public boolean isEmailTaken(Email email) {
        return false;
    }

    //todo
    public Optional<UserId> findUserByEmail(String email){
        return Optional.of(new UserId(UUID.randomUUID()));
    }


    /* TODO:
        write endpoint in users service that gives multiple users by its IDs
        Needed for SSO sessions (so if there are multiple sessions,
        there is no need to ask users service multiple times)
        return type should be List of minimal user data (username,email,logo uri)
     */
    public List<Void> findUsersByIds(List<UserId> userIds){
        return null;
    }

}
