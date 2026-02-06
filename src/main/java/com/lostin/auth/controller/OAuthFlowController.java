package com.lostin.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/auth/v1/signin")
public class OAuthFlowController {

    @GetMapping("/choose-account")
    public String chooseAccount() {
        return "oauth_flow_page/choose_account";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(name = "flow_id") UUID flowId,
            @RequestParam(name = "remember_me") Boolean rememberMe // saves user session in browser
    ) {
        return "oauth_flow_page/login";
    }

    @GetMapping("/register")
    public String register() {
        return "oauth_flow_page/register";
    }

    @GetMapping("/2mfa")
    public String twoFactorAuth() {
        return "oauth_flow_page/2mfa";
    }

    @GetMapping("/consent")
    public String consent() {
        return "oauth_flow_page/consent";
    }

    @GetMapping("/error")
    public String error() {
        return "oauth_flow_page/error";
    }
}
