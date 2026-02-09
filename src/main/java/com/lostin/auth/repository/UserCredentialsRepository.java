package com.lostin.auth.repository;

import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.model.proxy.UserCredentialsProxy;

import java.util.Optional;

public interface UserCredentialsRepository {

    Optional<UserCredentialsProxy> findCredentialsByUserId(UserId userId);
    UserCredentialsProxy saveCredentials(UserCredentialsProxy credentials);
    void deleteCredentials(UserId userId);

}
