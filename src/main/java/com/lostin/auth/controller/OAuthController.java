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
            @RequestParam(name = "response_type") String responseType,
            @RequestParam(name = "client_id") String clientId,
            @RequestParam(name = "redirect_uri") String redirectUri,
            @RequestParam(name = "scope") String scope,
            @RequestParam(name = "state", required = false) String state
    ) {
        OAuthorizeRequest request;
        //todo: check the redirect uri first, if its wrong return 400

        try {
            request = OAuthorizeRequest.builder()
                    .responseType(ResponseType.valueOf(responseType))
                    .clientId(clientId)
                    .redirectUri(redirectUri)
                    .scope(scope)
                    .state(state)
                    .build();
        } catch (ValidationException e) {
            URI location = UriComponentsBuilder
                    .fromUriString(redirectUri)
                    .queryParam("error","invalid_request")
                    .queryParam("error_description",e.getMessage())
                    .build(true).toUri();
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(location)
                    .build();
        }

        URI nextLocation = oAuthService.authorize(request);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(nextLocation)
                .build();
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
