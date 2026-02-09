package com.lostin.auth.service;


import com.lostin.auth.exception.ServiceResponseException;
import com.lostin.auth.model.core.auth_token.RefreshToken;
import com.lostin.auth.model.core.auth_token.Tokens;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.repository.Cache;
import com.lostin.auth.request_response.basic_auth_flow.BasicAuthRegisterRequest;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicAuthService {

    private final TokenService tokenService;
    private final UserService userService;
    private final Cache cache;

    public void login(BasicAuthLoginRequest request){
        /*todo: take user id from cache, if not found fetch from user service
            validate password
            if wrong password throw exception
            if ok don't do anything
         */
    }

    public Tokens register(BasicAuthRegisterRequest request){
        UserId userId = userService.createUser(request.email(),request.username());
        //todo
        return null;
    }

    public void logout(RefreshToken refreshToken){
    }

    public void emailExists(Email email){
        UserId userId = userService.findUserByEmail(email).orElseThrow(
                ()-> new ServiceResponseException(404,"USER_NOT_FOUND","User with email "+email+" not found")
        );
        userId.validate();
        cache.put("email:userid-"+email.value(),userId.value(),600); ///10 minutes in cache
    }

}
