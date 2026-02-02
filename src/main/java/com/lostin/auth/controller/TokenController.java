package com.lostin.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/token")
public class TokenController {

    @GetMapping("/public-key")
    public String getPublicKey(){
        return "public key";
    }

}
