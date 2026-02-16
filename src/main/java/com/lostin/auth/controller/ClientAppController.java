package com.lostin.auth.controller;

import com.lostin.auth.request_response.client_service.request.CreateClientRequest;
import com.lostin.auth.request_response.client_service.response.CreateClientResponse;
import com.lostin.auth.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller where user can create client apps
 */

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientAppController {

    private final ClientService clientService;

    /**
     * Gets user id from Access Token.
     * Creates a client app for a user
     */
    @PostMapping("/create")
    public ResponseEntity<CreateClientResponse> createClient(
            @Valid @RequestBody CreateClientRequest request
    ){
        var response = clientService.createClientApp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/create/test")
    public ResponseEntity<CreateClientRequest> testCreateClient(
            @RequestBody CreateClientRequest request
    ){
//        var response = clientService.createClientApp(request);
        return ResponseEntity.ok(request);
    }

    /**
     * Gets user id from Access Token.
     * Returns all client apps for user
     */
    @GetMapping("/get-clients")
    public ResponseEntity<String> getClients(){
        //todo: not implemented
        return null;
    }

    /**
     * check if secret is valid, because server won't save secret in database only hash
     */
    @PostMapping("/check-secret")
    public ResponseEntity<String> checkSecret(){
        //todo: not implemented
        return null;
    }




}
