package com.lostin.auth.request_response.oauth_flow.request;

import com.lostin.auth.request_response.oauth_flow.enums.ResponseType;
import lombok.Builder;

@Builder
public class OAuthorizeRequest {

    final ResponseType responseType;
    final String redirectUri;
    final String clientId;
    final String scope;
    final String state;

}
