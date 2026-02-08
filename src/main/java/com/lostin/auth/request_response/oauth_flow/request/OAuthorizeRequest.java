package com.lostin.auth.request_response.oauth_flow.request;

import com.lostin.auth.model.core.oauth_flow.ResponseType;
import lombok.Builder;

import java.util.UUID;

@Builder
public class OAuthorizeRequest {

    final UUID clientId;
    final ResponseType responseType;
    final String redirectUri;
    final String scope;
    final String state;

}
