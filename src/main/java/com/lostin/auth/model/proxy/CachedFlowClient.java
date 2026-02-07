package com.lostin.auth.model.proxy;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class CachedFlowClient {
    private final String clientId;
    private final String secretHash;
    private String authCode;

    private final String redirectUri;
    private final String state;
    private final boolean trusted;
    private final Set<String> scopes;

    private final boolean requirePkce;
    private final String codeChallenge;
    private final String codeChallengeMethod;

    // for consent page
    private final String appName;
    private final String appDescription;
    private final String appLogoUri;
}
