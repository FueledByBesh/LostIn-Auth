package com.lostin.auth.service;


import com.lostin.auth.exception.AlreadyExistException;
import com.lostin.auth.exception.NotFoundException;
import com.lostin.auth.exception.ServerError;
import com.lostin.auth.model.core.auth_token.RefreshToken;
import com.lostin.auth.model.core.auth_token.Tokens;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.model.proxy.UserCredentialsProxy;
import com.lostin.auth.repository.Cache;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthRegisterRequest;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthLoginRequest;
import com.lostin.auth.util.Hasher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicAuthService {

    private final TokenService tokenService;
    private final UserService userService;
    private final Cache cache;

    public boolean login(BasicAuthLoginRequest request) throws NotFoundException {
        /* todo:
            - take user id from cache, if not found fetch from user service
            - validate password
         */
        UserId userId = null;
        Optional<UserId> optionalUserId = cache.get("email:userid-" + request.email().value())
                .map(UserId::validated);
        userId = optionalUserId.orElseGet(
                () -> userService.findUserByEmail(request.email()).orElseThrow(
                        () -> new NotFoundException("USER_NOT_FOUND", "User with email " + request.email() + " not found")
                )
        );

        try {
            return userService.validateCredentials(
                    userId,
                    Hasher.passwordHasher(request.password().value(), "saltToHashPassword")
            );
        }catch (NotFoundException e){
            log.error("User data conflict: User found in Users Service, but not found in Auth Service",e);
            throw new ServerError("User data conflict");
        }

    }

    public void register(BasicAuthRegisterRequest request) throws AlreadyExistException {
        UserId userId = userService.createUser(request.email(), request.username());
        /*
         * TODO: create server secret for password hashing (like versionable JWKs but named salt).
         *  Into credentials entity put password hash and saltId.
         */
        String hashedPassword = Hasher.passwordHasher(request.password().value(), "saltToHashPassword");
        UserCredentialsProxy credentials = UserCredentialsProxy.builder()
                .userId(userId)
                .passwordHash(hashedPassword)
                .build();
        userService.saveUserCredentials(credentials);
    }

    public void logout(RefreshToken refreshToken) {
    }

    public void findEmailAndCache(Email email) throws NotFoundException{
        UserId userId = userService.findUserByEmail(email).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND", "User with email " + email + " not found")
        );
        cache.put("email:userid-" + email.value(), userId.value().toString(), 600); ///10 minutes in cache
    }

    /**
     * @param email email that should be checked
     * @return true if email not taken, otherwise false
     */
    public boolean isEmailAvailable(Email email) {
        return userService.findUserByEmail(email).isEmpty();
    }

}
