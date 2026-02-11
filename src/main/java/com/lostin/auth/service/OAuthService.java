package com.lostin.auth.service;

import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.model.core.oauth_flow.CachedFlow;
import com.lostin.auth.model.core.oauth_flow.CodeChallengeMethod;
import com.lostin.auth.model.core.oauth_flow.CachedFlowClient;
import com.lostin.auth.model.proxy.ClientProxy;
import com.lostin.auth.repository.ClientRepository;
import com.lostin.auth.repository.OAuthFlowRepository;
import com.lostin.auth.model.core.oauth_flow.ResponseType;
import com.lostin.auth.request_response.oauth_flow.request.OAuthorizeRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class OAuthService {

    private final String APP_BASE_URL;
    private final ClientRepository clientRepository;
    private final OAuthFlowRepository oAuthFlowRepository;

    public OAuthService(
            @Value("${AUTH_APP_BASE_URL}") String APP_BASE_URL,
            @Qualifier("jpa-impl") ClientRepository clientRepository,
            @Qualifier("redis-impl")
            OAuthFlowRepository oAuthFlowRepository
    ) {
        this.APP_BASE_URL = APP_BASE_URL;
        this.clientRepository = clientRepository;
        this.oAuthFlowRepository = oAuthFlowRepository;
    }

    /**
     * @param request metadata for OAuth flow
     * @return URI location to next step with OAuth flow id param
     */
    public URI authorize(OAuthorizeRequest request){
        return null;
    }

    /*
        TODO: authorize method shouldn't return ResponseEntity (its controllers job)
            1) For future rewrite this method and find easier approach for validation
            2) Define another Exception classes for OAuth requests (like OAuthAuthorizeException, OAuthRedirectException and etc)
            and write handle methods for them in GlobalExceptionHandler
     */
    public ResponseEntity<Void> authorize(Map<String,String> params){
        if(!params.containsKey("client_id")){
            throw new BadRequestException("INVALID_PARAMS","client_id is required");
        }
        UUID clientId;
        try {
            clientId = UUID.fromString(params.get("client_id"));
        }catch (IllegalArgumentException e){
            throw new BadRequestException("INVALID_PARAMS","Invalid client_id");
        }
        /* todo: Check client status
            if revoked return error
            if suspended validate more
            if active let it pass
         */

        ClientProxy client = clientRepository.findClientById(clientId).orElseThrow(
                () -> new BadRequestException("INVALID_CLIENT_ID","Client not found")
        );

        if(!params.containsKey("redirect_uri")){
            throw new BadRequestException("INVALID_PARAMS","redirect_uri is required");
        }
        String redirectUri = params.get("redirect_uri");
        if(!client.getRedirectUris().contains(redirectUri)){
            throw new BadRequestException("INVALID_REDIRECT_URI","Invalid redirect_uri");
        }

        if(!params.containsKey("response_type")){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(
                            UriComponentsBuilder.fromUriString(redirectUri)
                                    .queryParam("error","invalid_request")
                                    .queryParam("error_description","response_type is required")
                                    .queryParam("state",params.get("state"))
                                    .build().toUri()
                    )
                    .build();
        }
        ResponseType responseType;
        try {
            responseType = ResponseType.from(params.get("response_type"));
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(
                            UriComponentsBuilder.fromUriString(redirectUri)
                                    .queryParam("error","invalid_request")
                                    .queryParam("error_description",e.getMessage())
                                    .queryParam("state",params.get("state"))
                                    .build().toUri()
                    ).build();
        }

        if(!params.containsKey("scope") || params.get("scope").isBlank()){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(
                            UriComponentsBuilder.fromUriString(redirectUri)
                                    .queryParam("error","invalid_request")
                                    .queryParam("error_description","scope is required")
                                    .queryParam("state",params.get("state"))
                                    .build().toUri()
                    ).build();
        }
        Set<String> scopes = Set.of(params.get("scope").split(" "));
        Set<String> allowedScopes = client.getAllowedScopes();
        List<String> notAllowedScopes = scopes.stream().filter(scope -> !allowedScopes.contains(scope)).toList();

        if(!notAllowedScopes.isEmpty()){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(
                            UriComponentsBuilder.fromUriString(redirectUri)
                                    .queryParam("error","access_denied")
                                    .queryParam("error_description","Not allowed scopes: " + String.join(" ",notAllowedScopes))
                                    .queryParam("state",params.get("state"))
                                    .build().toUri()
                    ).build();
        }

        boolean requirePkce = client.getRequirePkce();
        String codeChallenge = null;
        CodeChallengeMethod codeChallengeMethod = null;
        if(!params.containsKey("code_challenge")){
            if(client.getRequirePkce()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(
                                UriComponentsBuilder.fromUriString(redirectUri)
                                        .queryParam("error", "access_denied")
                                        .queryParam("error_description", "PKCE is required")
                                        .queryParam("state", params.get("state"))
                                        .build().toUri()
                        ).build();
            }
        }
        else{
            if(!params.containsKey("code_challenge_method")){
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(
                                UriComponentsBuilder.fromUriString(redirectUri)
                                        .queryParam("error", "invalid_request")
                                        .queryParam("error_description", "code_challenge_method is required")
                                        .queryParam("state", params.get("state"))
                                        .build().toUri()
                        ).build();
            }
            try {
                codeChallengeMethod = CodeChallengeMethod.from(params.get("code_challenge_method"));
            }catch (ValidationException e){
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(
                                UriComponentsBuilder.fromUriString(redirectUri)
                                        .queryParam("error", "invalid_request")
                                        .queryParam("error_description", e.getMessage())
                                        .queryParam("state", params.get("state"))
                                        .build().toUri()
                        ).build();
            }
            codeChallenge = params.get("code_challenge");
            requirePkce = true;
        }


        CachedFlowClient cachedFlowClient = CachedFlowClient.builder()
                .clientId(client.getId())
                .secretHash(client.getSecretHash())
                .redirectUri(redirectUri)
                .state(params.get("state"))
                .trusted(client.getTrusted())
                .scopes(scopes)
                .requirePkce(requirePkce)
                .codeChallenge(codeChallenge)
                .codeChallengeMethod(codeChallengeMethod)
                .appName(client.getName())
                .appDescription(client.getDescription())
                .appLogoUri(client.getLogoUri())
                .build();

        CachedFlow flow = oAuthFlowRepository.persistFlow(cachedFlowClient);
        String chooseAccountPageUri = APP_BASE_URL+"/auth/v1/sign-in/choose-account-page";
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(UriComponentsBuilder.fromUriString(chooseAccountPageUri)
                        .queryParam("fid",flow.getFlowId().toString())
                        .build().toUri()
                ).build();
    }
}
