package com.lostin.auth.controller;

import com.lostin.auth.service.ClientAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller where user can create client apps
 */

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientAppController {

    private final ClientAppService clientAppService;

    /**
     * Gets user id from Access Token.
     * Creates a client app for a user
     */
    @PostMapping("/create-client")
    public ResponseEntity<String> createClient(){
        return null;
    }

    /**
     * Gets user id from Access Token.
     * Returns all client apps for user
     */
    @GetMapping("/get-clients")
    public ResponseEntity<String> getClients(){
        return null;
    }

    /**
     * check if secret is valid, because server won't save secret in database only hash
     */
    @PostMapping("/check-secret")
    public ResponseEntity<String> checkSecret(){
        return null;
    }




}
