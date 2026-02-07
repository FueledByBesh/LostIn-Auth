package com.lostin.auth.service;

import com.lostin.auth.exception.BadRequestException;
import com.lostin.auth.exception.ValidationException;
import com.lostin.auth.model.proxy.ClientProxy;
import com.lostin.auth.repository.ClientRepository;
import com.lostin.auth.repository.OAuthFlowRepository;
import com.lostin.auth.request_response.oauth_flow.enums.ResponseType;
import com.lostin.auth.request_response.oauth_flow.request.OAuthorizeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final ClientRepository clientRepository;
    private final OAuthFlowRepository oAuthFlowRepository;

    public OAuthService(
            @Qualifier("jpa-impl") ClientRepository clientRepository,
            @Qualifier("redis-impl")
            OAuthFlowRepository oAuthFlowRepository
    ) {
        this.clientRepository = clientRepository;
        this.oAuthFlowRepository = oAuthFlowRepository;
    }

    /**
     *
     * @param request metadata for OAuth flow
     * @return URI location to next step with OAuth flow id param
     */
    public URI authorize(OAuthorizeRequest request){
        return null;
    }

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
                                    .build(true).toUri()
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
                                    .build(true).toUri()
                    ).build();
        }

        if(!params.containsKey("scope") || params.get("scope").isBlank()){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(
                            UriComponentsBuilder.fromUriString(redirectUri)
                                    .queryParam("error","invalid_request")
                                    .queryParam("error_description","scope is required")
                                    .queryParam("state",params.get("state"))
                                    .build(true).toUri()
                    ).build();
        }
        Set<String> scopes = Set.of(params.get("scope").split(" "));
        Set<String> allowedScopes = client.getAllowedScopes();
        List<String> notAllowedScopes = scopes.stream().filter(scope -> !allowedScopes.contains(scope)).toList();

        if(!notAllowedScopes.isEmpty()){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(
                            UriComponentsBuilder.fromUriString(redirectUri)
                                    .queryParam("error","invalid_request")
                                    .queryParam("error_description","Not allowed scopes: " + String.join(" ",notAllowedScopes))
                                    .queryParam("state",params.get("state"))
                                    .build(true).toUri()
                    ).build();
        }

        // todo: Require pcke if client is public (code_challenge, code_challenge_method)
        /* todo: Check client status
            if revoked return error
            if suspended validate more
            if active let it pass
         */
        return null;
    }
}
