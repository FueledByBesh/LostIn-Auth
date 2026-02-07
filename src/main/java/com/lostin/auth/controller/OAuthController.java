package com.lostin.auth.controller;

import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.request_response.oauth_flow.enums.ResponseType;
import com.lostin.auth.request_response.oauth_flow.request.OAuthorizeRequest;
import com.lostin.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1/oauth2")
public class OAuthController {

    private final OAuthService oAuthService;


    /**
     * Redirects to authorize page if request parameters are valid,
     * Creates Auth flow saves context in redis until user authorized.
     */
    @GetMapping("/authorize")
    public ResponseEntity<Void> authorize(
            @RequestParam Map<String, String> params
    ) {
        return oAuthService.authorize(params);
    }


    @PostMapping("/token")
    public ResponseEntity<String> getToken(
            @RequestParam(name = "client_id") String clientId,
            @RequestParam(name = "client_secret") String clientSecret,
            @RequestParam(name = "grant_type") String grantType,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "redirect_uri") String redirectUri
    ) {
        return null;
    }

}
