package com.lostin.auth.controller;


import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.model.core.user.Email;
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
    public ResponseEntity<Void> login(
            @RequestBody BasicAuthLoginRequest request /// already validated
    ) {
        basicAuthService.login(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody BasicAuthRegisterRequest request
    ) {
        return ResponseEntity.ok("User Registered");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping("/login/validate-email")
    public ResponseEntity<Void> validateEmail(
            @RequestBody String email
    ){
        Email validatedEmail;
        try {
            validatedEmail = Email.validated(email);
        }catch (ValidationException e){
            throw new BadRequestException(e.getError(),e.getMessage());
        }
        basicAuthService.emailExists(validatedEmail);
        return ResponseEntity.ok().build();
    }

}
