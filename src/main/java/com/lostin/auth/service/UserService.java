package com.lostin.auth.service;

import com.lostin.auth.external_api.LostInUsersApi;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.model.core.user.Username;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final LostInUsersApi usersApi;

    //todo
    public UserId createUser(Email email, Username username){
        email.validate();
        username.validate();
        return usersApi.createUser(email.value(),username.value());
    }

    //todo
    public Optional<UserId> findUserByEmail(Email email){
        email.validate();
        return usersApi.findUserByEmail(email.value());
    }

}
