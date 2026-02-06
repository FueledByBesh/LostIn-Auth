package com.lostin.auth.controller;


import com.lostin.auth.request_response.basic_auth_flow.BasicAuthRegisterRequest;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthLoginRequest;
import com.lostin.auth.service.BasicAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
For internal services use only, not for all clients (They will use OAuth via client id and client secret).
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class BasicAuthController {

    private final BasicAuthService basicAuthService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody BasicAuthLoginRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Login not implemented yet");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody BasicAuthRegisterRequest request
    ) {
        return ResponseEntity.ok("User Registered");
    }

    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }

}
