package com.lostin.auth;

import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.request_response.basic_auth_flow.request.BasicAuthLoginRequest;
import org.junit.jupiter.api.Test;

public class ValidationTest {


    @Test
    void emailValidation(){
        String email = "olzhasakimbai@gmail.com";
        try {
            Email email1 = new Email(email);
            email1.validate();
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
            new BasicAuthLoginRequest(email, password).validate();
        }catch (BadRequestException e){
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("validated successfully");

    }
}
