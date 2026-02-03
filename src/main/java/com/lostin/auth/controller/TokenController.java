package com.lostin.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/token")
public class TokenController {

    @GetMapping("/jwks")
    public String getPublicKeys(){
        return "public key";
    }

    @GetMapping("/jwk")
    public String getPublicKey(
            @RequestParam String keyId
    ){
        return "public key";
    }

}
