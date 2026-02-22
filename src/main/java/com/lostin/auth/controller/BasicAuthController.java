package com.lostin.auth.controller;


import com.lostin.auth.exception.*;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthRegisterRequest;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthLoginRequest;
import com.lostin.auth.request_response.basic_auth_flow.request.EmailRequest;
import com.lostin.auth.request_response.basic_auth_flow.resopnse.BasicAuthLoginResponse;
import com.lostin.auth.request_response.basic_auth_flow.resopnse.BasicAuthRegisterResponse;
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

import java.util.Optional;


/**
 * For internal services use only, not for all clients (They will use OAuth via client id and client secret).
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class BasicAuthController {

    private final BasicAuthService basicAuthService;


    /**
     * @param request request body with email and password
     * @return code 200 if login successful with user id
     * code 400 if request body is invalid,
     * code 401 if wrong credentials,
     * code 404 if user not found.
     */
    @PostMapping("/login")
    public ResponseEntity<BasicAuthLoginResponse> login(
            @Valid @RequestBody BasicAuthLoginRequest request
    ) {
        Optional<String> optionalIdToken = basicAuthService.login(request);
        if (optionalIdToken.isEmpty())
            throw new UnAuthorizedException("Wrong Credentials");
        return ResponseEntity.ok(new BasicAuthLoginResponse(optionalIdToken.get()));
    }

    /**
     * @param request request body with email, username, and password
     * @return code 201 (Created) if registration successful with user id
     * code 400 (Bad Request) if request body is invalid,
     * code 409 (Conflict) if user already exists,
     */
    @PostMapping("/register")
    public ResponseEntity<BasicAuthRegisterResponse> registerUser(
            @Valid @RequestBody BasicAuthRegisterRequest request
    ) {
        String token = basicAuthService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BasicAuthRegisterResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /**
     * @return code 200 (Ok) if email not taken,
     * code 409 (Conflict) if email is already taken,
     * code 400 (Bad Request) if email is invalid
     */
    @PostMapping("/register/email-available")
    public ResponseEntity<Void> isEmailAvailable(
            @Valid @RequestBody EmailRequest request
    ) {
        if (basicAuthService.isEmailAvailable(new Email(request.email()))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    /**
     * @return code 200 (Ok) if email found and cached,
     * code 404 (Not Found) if email not found,
     * code 400 (Bad Request) if email is invalid
     */
    @PostMapping("/login/validate-email")
    public ResponseEntity<Void> validateEmail(
            @Valid @RequestBody EmailRequest request
    ) {
        try {
            basicAuthService.findEmailAndCache(new Email(request.email()));
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
