package com.lostin.auth.controller;

import com.lostin.auth.dto.thymeleaf.Client;
//import com.lostin.auth.dto.thymeleaf.Session;
import com.lostin.auth.exception.NotFoundException;
import com.lostin.auth.model.core.oauth_flow.CachedFlowClient;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.repository.Cache;
import com.lostin.auth.repository.impl.cache.CachingOption;
import com.lostin.auth.service.OAuthFlowService;
import com.lostin.auth.service.SSOSessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping(name = "oauth-flow", path = "/auth/v1/sign-in")
public class OAuthFlowController {

    private final String APP_BASE_URL;
    private final OAuthFlowService flowService;
    private final SSOSessionService sessionService;
    private final Cache cache;

    public OAuthFlowController(
            @Value("${AUTH_APP_BASE_URL}") String APP_BASE_URL,
            OAuthFlowService flowService,
            SSOSessionService sessionService,
            Cache cache
    ) {
        this.APP_BASE_URL = APP_BASE_URL;
        this.flowService = flowService;
        this.sessionService = sessionService;
        this.cache = cache;
    }

    @GetMapping("/choose-account-page")
    public String chooseAccountPage(
            @RequestParam(name = "fid") UUID flowId
    ) {
        /*
            TODO:
                check cookies for sid (session id)
                if there is session id validate them from SSOSessionRepository and cache them for quick access
                if there is no session id or they're not validated redirect to login page
         */
        return "redirect:" +
                APP_BASE_URL +
                "/auth/v1/sign-in" +
                "/login-page?fid=" + flowId;

//        Set<Session> sessions = new HashSet<>();
//        // there will be code for extracting session ids from cookies
//        if (!sessions.isEmpty()) {
//            model.addAttribute("flowId", flowId);
//            model.addAttribute("sessions", sessions);
//            return "oauth_flow_pages/choose-account-page";
//        } else {
//            return "redirect:" +
//                    APP_BASE_URL +
//                    "/auth/v1/sign-in" +
//                    "/login-page?fid=" + flowId;
//        }

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
            @RequestParam(name = "token") String idToken,
            @RequestParam(name = "remember_me", defaultValue = "false") boolean rememberMe // saves user session in browser
    ) {
        Optional<String> optionalUid = cache.get(CachingOption.OPAQUE_TOKEN_TO_UID,idToken);
        if(optionalUid.isEmpty()) {
            return "redirect:" +
                    APP_BASE_URL +
                    "/auth/v1/sign-in/error-page";
        }

        UserId userIdValidated = UserId.validated(optionalUid.get());
        // saves user in flow
        try {
            flowService.saveUserIntoFlow(flowId, userIdValidated);
        }catch (NotFoundException e){
            return "redirect:" +
                    APP_BASE_URL +
                    "/auth/v1/sign-in/error-page";
        }
        if(rememberMe) {
            UUID sessionId = sessionService.saveSession(userIdValidated);
            //TODO: Save sessionId in cookies
        }

        // redirects to consent page
        return "redirect:" +
                APP_BASE_URL +
                "/auth/v1/sign-in/consent-page?" +
                "fid=" + flowId;
    }

    @GetMapping("/2mfa")
    public String twoFactorAuth() {
        return "not-implemented-yet";
    }

    @GetMapping("/consent-page")
    public String consentPage(
            @RequestParam(name = "fid") UUID flowId,
            Model model
    ) {
        /* TODO:
            - take client app profile from flow
            - write consent text
            - put consent text and client profile into model
         */
        Client aboutApp;
        String consentText;

        try {
            CachedFlowClient client = flowService.getClient(flowId);
            aboutApp = Client.builder()
                    .appName(client.getAppName())
                    .description(client.getAppDescription())
                    .logoUri(client.getAppLogoUri())
                    .build();

            consentText = generateConsentText(client);
        } catch (NotFoundException e){
            return "redirect:" +
                    APP_BASE_URL +
                    "/auth/v1/sign-in/error-page";
        }
        model.addAttribute("flowId", flowId);
        model.addAttribute("client", aboutApp);
        model.addAttribute("consent", consentText);

        return "oauth_flow_pages/consent-page";
    }

    @GetMapping("/authorize-client")
    public String authorizeClient(
            @RequestParam(name = "fid") UUID flowId,
            @RequestParam(name = "granted") boolean granted
    ) {
        /// Todo: Decides whether to give permission or not and redirects to required location
        System.out.println(flowId);
        System.out.println(granted);
        return null;
    }

    @GetMapping("/error-page")
    public String errorPage(){
        return "oauth_flow_pages/error-page";
    }

    private String generateConsentText(CachedFlowClient client){
        String text = """
                Client app %s wants to access your account.
                
                It will be able to:
                """.formatted(client.getAppName());
        StringBuilder sb = new StringBuilder(text);
        client.getScopes().forEach(scope -> sb.append("- ").append(scope).append("\n"));
        sb.append("Will you give permission?");

        return sb.toString();
    }
}
