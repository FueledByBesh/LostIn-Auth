package com.lostin.auth.controller;

import com.lostin.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "oauth2.0",path="/auth/v1/oauth2")
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
