package com.lostin.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping(name = "oauth-flow",path = "/auth/v1/signin")
public class OAuthFlowController {

    private final String APP_BASE_URL;

    public OAuthFlowController(
            @Value("${AUTH_APP_BASE_URL}") String APP_BASE_URL
    ) {
        this.APP_BASE_URL = APP_BASE_URL;
    }

    @GetMapping("/choose-account-page")
    public String chooseAccountPage(
            @RequestParam(name = "fid") UUID flowId
    ) {
        /*
            TODO:
                check cookies for sid (session id)
                if there is session id validate them from SSOSessionRepository
                if there is no session id or they're not validated redirect to login page
         */

        return "redirect:" +
                APP_BASE_URL +
                "/auth/v1/signin" +
                "/login-page?fid=" + flowId;
//        return "oauth_flow_page/choose_account";
    }

    @GetMapping("/choose-account")
    public String chooseAccount(
            @RequestParam(name = "fid") UUID flowId
    ) {
        return null;
    }

    /*
        TODO:
            Uses BasicAuthController endpoints for login.
            if
     */
    @GetMapping("/login-page")
    public String loginPage(
            @RequestParam(name = "fid") UUID flowId
    ) {
        return "oauth_flow_page/login-page";
    }

    @GetMapping("/register-page")
    public String registerPage() {
        return "oauth_flow_page/register-page";
    }

    /*
        TODO:
            1) Intermediate endpoint after login (or register) page and consent page
            2) Saves user data in flow
            3) Doesn't return page just redirects to next page
     */
    @GetMapping("/authenticate")
    public String authenticate(
            @RequestParam(name = "fid") UUID flowId,
            @RequestParam(name = "remember_me",defaultValue = "false") boolean rememberMe // saves user session in browser
    ) {
        return null;
    }

    @GetMapping("/2mfa")
    public String twoFactorAuth() {
        return "oauth_flow_page/2mfa";
    }

    @GetMapping("/consent-page")
    public String consent() {
        return "oauth_flow_page/consent-page";
    }

    @GetMapping("/error")
    public String error() {
        return "oauth_flow_page/error-page";
    }
}
