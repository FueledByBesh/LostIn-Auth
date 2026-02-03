package com.lostin.auth.service;


import com.lostin.auth.model.core.auth_token.RefreshToken;
import com.lostin.auth.model.core.auth_token.Tokens;
import com.lostin.auth.model.core.user.UserId;
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

    public Tokens login(BasicAuthLoginRequest request){
        return null;
    }

    public Tokens register(BasicAuthRegisterRequest request){
        UserId userId = userService.createUser(request.email(),request.username());
        //todo
        return null;
    }

    public void logout(RefreshToken refreshToken){
    }

}
