package com.lostin.auth.controller;

import com.lostin.auth.dto.thymeleaf.Client;
import com.lostin.auth.dto.thymeleaf.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(name = "oauth-flow", path = "/auth/v1/sign-in")
public class OAuthFlowController {

    private final String APP_BASE_URL;

    public OAuthFlowController(
            @Value("${AUTH_APP_BASE_URL}") String APP_BASE_URL
    ) {
        this.APP_BASE_URL = APP_BASE_URL;
    }

    @GetMapping("/choose-account-page")
    public String chooseAccountPage(
            @RequestParam(name = "fid") UUID flowId,
            Model model
    ) {
        /*
            TODO:
                check cookies for sid (session id)
                if there is session id validate them from SSOSessionRepository and cache them for quick access
                if there is no session id or they're not validated redirect to login page
         */
        List<Session> sessions = new ArrayList<>();
        // there will be code for extracting session ids from cookies
        if (!sessions.isEmpty()) {
            model.addAttribute("flowId", flowId);
            model.addAttribute("sessions", sessions);
            return "oauth_flow_pages/choose-account-page";
        } else {
            return "redirect:" +
                    APP_BASE_URL +
                    "/auth/v1/sign-in" +
                    "/login-page?fid=" + flowId;
        }

    }

    @GetMapping("/choose-account")
    public String chooseAccount(
            @RequestParam(name = "fid") UUID flowId,
            Model model
    ) {
        /* Todo:
            - takes session ids from cookies and validates them
            - Gets user profile (email,username,avatar_url) from user service
            - Puts into Model to render them in page using thymeleaf
         */
        model.addAttribute("flowId", flowId);
        return null;
    }

    @GetMapping("/account-chosen")
    public String accountChosen(
            @RequestParam(name = "fid") UUID flowId,
            @RequestParam(name = "sid") String sessionId
    ) {
        //todo: decides whether to require password or pass without it and redirects to next page
        return null;
    }

    @GetMapping("/login-page")
    public String loginPage(
            @RequestParam(name = "fid") UUID flowId,
            Model model
    ) {
        model.addAttribute("flowId", flowId);
        return "oauth_flow_pages/login-page";
    }

    @GetMapping("/register-page")
    public String registerPage(
            @RequestParam(name = "fid") UUID flowId,
            Model model
    ) {
        model.addAttribute("flowId", flowId);
        return "oauth_flow_pages/register-page";
    }

    /*
        TODO:
            1) Intermediate endpoint after login (or register) page and consent page
            2) Saves user data in flow
            3) Doesn't return page just redirects to next page
     */
    @GetMapping("/authenticated")
    public String authenticated(
            @RequestParam(name = "fid") UUID flowId,
            @RequestParam(name = "remember_me", defaultValue = "false") boolean rememberMe // saves user session in browser
    ) {
        return null;
    }

    @GetMapping("/2mfa")
    public String twoFactorAuth() {
        return "oauth_flow_page/2mfa";
    }

    @GetMapping("/consent-page")
    public String consent(
            @RequestParam(name = "fid") UUID flowId,
            Model model
    ) {
        /* TODO:
            - take client app profile from flow
            - write consent text
            - put consent text and client profile into model
         */
        Client aboutApp = null;
        String consentText = "";

        model.addAttribute("flowId", flowId);
        model.addAttribute("client", aboutApp);
        model.addAttribute("consent", consentText);

        return "oauth_flow_pages/consent-page";
    }

    @GetMapping("/authorize-client")
    public String authorizeClient(
            @RequestParam(name = "fid") UUID flowId,
            @RequestParam(name = "access-given") boolean accessGiven
    ) {
        /// Todo: Decides whether to give permission or not and redirects to required locaiton

        return null;
    }
}
