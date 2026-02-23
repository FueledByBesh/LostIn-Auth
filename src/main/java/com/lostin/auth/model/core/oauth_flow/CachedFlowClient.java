package com.lostin.auth.model.core.oauth_flow;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CachedFlowClient {
    private final UUID clientId;
    private final String secretHash;

    private final String redirectUri;
    private final String state;
    private final boolean trusted;
    private final Set<String> scopes;

    private final boolean requirePkce;
    private final String codeChallenge;
    private final CodeChallengeMethod codeChallengeMethod;

    // for consent page
    private final String appName;
    private final String appDescription;
    private final String appLogoUri;
}
