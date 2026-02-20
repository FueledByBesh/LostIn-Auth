package com.lostin.auth.service;


import com.lostin.auth.exception.AlreadyExistException;
import com.lostin.auth.exception.NotFoundException;
import com.lostin.auth.exception.ServerError;
import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.Password;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.model.core.user.Username;
import com.lostin.auth.repository.Cache;
import com.lostin.auth.repository.impl.cache.CachingOption;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthRegisterRequest;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthLoginRequest;
import com.lostin.auth.util.OpaqueTokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicAuthService {

    private final UserService userService;
    private final UserCredentialsService credentialsService;
    private final Cache cache;


    /**
     *
     * @param request email and password in {@link BasicAuthLoginRequest object wrapper}
     * @return user id if login successful, otherwise empty
     * @throws NotFoundException if user not found
     */
    public Optional<String> login(BasicAuthLoginRequest request) throws NotFoundException {
        /* done:
            - take user id from cache, if not found, fetch from user service
            - validate password
         */
        UserId userId;
        Optional<UserId> optionalUserId;
        try {
            optionalUserId = cache.get(CachingOption.USER_EMAIL_TO_ID, request.email()).map(UserId::from);
        }catch (ValidationException e){
            log.warn("Invalid user Id in cache for email: {}",request.email(),e);
            optionalUserId = Optional.empty();
        }
        userId = optionalUserId.orElseGet(
                () -> userService.findUserByEmail(new Email(request.email())).orElseThrow(
                        () -> new NotFoundException("USER_NOT_FOUND", "User with email " + request.email() + " not found")
                )
        );

        try {
            if (credentialsService.validateCredentials(userId, new Password(request.password()))) {
                String opaqueId = OpaqueTokenGenerator.generateOpaqueToken();
                cache.put(CachingOption.OPAQUE_TOKEN_TO_UID, opaqueId, userId.value().toString(), 5, TimeUnit.MINUTES);
                return Optional.of(opaqueId);
            }
            return Optional.empty();
        } catch (NotFoundException e) {
            log.error("User data conflict: User found in Users Service, but not found in Auth Service", e);
            throw new ServerError("User data conflict");
        }

    }

    public String register(BasicAuthRegisterRequest request) throws AlreadyExistException {
        UserId userId = userService.createUser(new Email(request.email()), new Username(request.username()));
        credentialsService.createCredentials(userId, new Password(request.password()));
        String opaqueId = OpaqueTokenGenerator.generateOpaqueToken();
        cache.put(CachingOption.OPAQUE_TOKEN_TO_UID, opaqueId, userId.value().toString(), 5, TimeUnit.MINUTES);
        return opaqueId;
    }

    public void findEmailAndCache(Email email) throws NotFoundException {
        UserId userId = userService.findUserByEmail(email).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND", "User with email " + email.value() + " not found")
        );
        cache.put(CachingOption.USER_EMAIL_TO_ID, email.value(), userId.value().toString(), 10,TimeUnit.MINUTES);
    }

    /**
     * @param email email that should be checked
     * @return true if email not taken, otherwise false
     */
    //todo: use isEmailTaken endpoint in Users Service
    public boolean isEmailAvailable(Email email) {
        return userService.findUserByEmail(email).isEmpty();
    }

}
