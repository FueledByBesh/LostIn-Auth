package com.lostin.auth.controller;


import com.lostin.auth.exception.AlreadyExistException;
import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.exception.NotFoundException;
import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthRegisterRequest;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthLoginRequest;
import com.lostin.auth.request_response.basic_auth_flow.resopnse.BasicAuthLoginResponse;
import com.lostin.auth.request_response.basic_auth_flow.resopnse.BasicAuthRegisterResponse;
import com.lostin.auth.service.BasicAuthService;
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


    /**
     * @param request request body with email and password
     * @return code 200 if login successful
     *         code 400 if request body is invalid,
     *         code 401 if wrong credentials,
     *         code 404 if user not found
     */
    @PostMapping("/login")
    public ResponseEntity<BasicAuthLoginResponse> login(
            @RequestBody BasicAuthLoginRequest request /// already validated
    ) {
        try {
            boolean authenticated = basicAuthService.login(request);
            if (!authenticated)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new BasicAuthLoginResponse("Wrong Credentials"));
            return ResponseEntity.ok(new BasicAuthLoginResponse("Authorized Successfully"));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BasicAuthLoginResponse("User not found"));
        }
    }

    /**
     * @param request request body with email, username, and password
     * @return code 201 (Created) if registration successful
     *         code 400 (Bad Request) if request body is invalid,
     *         code 409 (Conflict) if user already exists,
     */
    @PostMapping("/register")
    public ResponseEntity<BasicAuthRegisterResponse> registerUser(
            @RequestBody BasicAuthRegisterRequest request /// already validated
    ) {
        try {
            basicAuthService.register(request);
        }catch (AlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new BasicAuthRegisterResponse(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BasicAuthRegisterResponse("User registered successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /**
     * @param email email to check
     * @return code 200 (Ok) if email not taken,
     *         code 409 (Conflict) if email is already taken,
     *         code 401 (Bad Request) if email is invalid
     */
    @PostMapping("/register/email-available")
    public ResponseEntity<Void> isEmailAvailable(
            @RequestBody String email
    ){
        Email validatedEmail;
        try {
            validatedEmail = Email.validated(email);
        }catch (ValidationException e){
            throw new BadRequestException(e.getError(),e.getMessage());
        }
        if(basicAuthService.isEmailAvailable(validatedEmail)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    /**
     * @param email email to check
     * @return code 200 (Ok) if email found and cached,
     *         code 404 (Not Found) if email not found,
     *         code 400 (Bad Request) if email is invalid
     */
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
        try {
            basicAuthService.findEmailAndCache(validatedEmail);
            return ResponseEntity.ok().build();
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
