package com.lostin.auth.service;

import com.lostin.auth.exception.AlreadyExistException;
import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.exception.NotFoundException;
import com.lostin.auth.exception.ServerError;
import com.lostin.auth.external_api.LostInUsersApi;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.model.core.user.Username;
import com.lostin.auth.model.proxy.UserCredentialsProxy;
import com.lostin.auth.repository.UserCredentialsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final LostInUsersApi usersApi;
    private final UserCredentialsRepository credentialsRepo;

    public UserId createUser(Email email, Username username) throws AlreadyExistException {
        try {
            return usersApi.createUser(email.value(), username.value());
        }catch (BadRequestException e){
            log.error(
                    "UNEXPECTED ERROR! Assumptions: " +
                            "1) Auth server parameter validation do not satisfy to User server validation" +
                            "2) Used wrong User server endpoint",
                    e);
            throw new ServerError();
        }
    }

    public Optional<UserId> findUserByEmail(Email email) {
        return usersApi.findUserByEmail(email.value());
    }

    public Optional<UserCredentialsProxy> findUserCredentialsByUserId(UserId userId) {
        return credentialsRepo.findCredentialsByUserId(userId);
    }

    public boolean validateCredentials(@NonNull UserId userId, @NonNull String passwordHash) throws NotFoundException {
        UserCredentialsProxy user = this.findUserCredentialsByUserId(userId).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND", "User with id: " + userId.value() + " not found")
        );
        return user.validatePassword(passwordHash);
    }

    public UserCredentialsProxy saveUserCredentials(UserCredentialsProxy credentials) {
        return credentialsRepo.saveCredentials(credentials);
    }

}
