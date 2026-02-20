package com.lostin.auth;

import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthLoginRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ValidationTest {


    @Test
    void emailValidation(){
        String email = "olzhasakimbai";
        try {
            new Email(email);
        }catch (ValidationException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("validated successfully");
    }

    @Test
    void userIdValidation() {
        UUID userId = null;
        try {
            new UserId(userId);
        }catch (ValidationException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("validated successfully");
    }

    @Test
    void validateBasicAuthRequest(){
        String email = "someemail";
        String password = "omePassword";

        try {
            new BasicAuthLoginRequest(email, password);
        }catch (BadRequestException e){
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("validated successfully");

    }
}
