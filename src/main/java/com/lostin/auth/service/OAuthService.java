package com.lostin.auth.service;

import com.lostin.auth.repository.ClientRepository;
import com.lostin.auth.request_response.oauth_flow.request.OAuthorizeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final ClientRepository clientRepository;

    /**
     *
     * @param request metadata for OAuth flow
     * @return URI location to next step with OAuth flow id param
     */
    public URI authorize(OAuthorizeRequest request){


        return null;
    }


    public void sendAuthorizationResponse(UUID flowId){}

    public boolean validateRedirectUri(String redirectUri){

        return false;
    }

}
