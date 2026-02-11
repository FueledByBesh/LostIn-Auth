package com.lostin.auth.service;

import com.lostin.auth.exception.NotFoundException;
import com.lostin.auth.model.core.user.Password;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.model.proxy.UserCredentialsProxy;
import com.lostin.auth.repository.UserCredentialsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCredentialsService {

    private final UserCredentialsRepository credentialsRepo;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserCredentialsProxy> findUserCredentialsByUserId(UserId userId) {
        return credentialsRepo.findCredentialsByUserId(userId);
    }

    public boolean validateCredentials(@NonNull UserId userId, @NonNull Password password) throws NotFoundException {
        UserCredentialsProxy user = this.findUserCredentialsByUserId(userId).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND", "User with id: " + userId.value() + " not found")
        );
        return this.validatePassword(user.getPasswordHash(), password);
    }

    public void createCredentials(UserId userId, Password password) {
        UserCredentialsProxy credentials = UserCredentialsProxy.builder()
                .userId(userId)
                .passwordHash(hashPassword(password.value()))
                .build();
        credentialsRepo.saveCredentials(credentials);
    }

    private String hashPassword(@NonNull String plainPass){
        return passwordEncoder.encode(plainPass);
    }

    private boolean validatePassword(@NonNull String hashedPass, @NonNull Password plainPass){
        return passwordEncoder.matches(plainPass.value(),hashedPass);
    }

}
