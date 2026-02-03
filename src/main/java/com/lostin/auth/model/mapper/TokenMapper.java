package com.lostin.auth.model.mapper;

import com.lostin.auth.model.entity.TokenEntity;
import com.lostin.auth.model.proxy.TokenProxy;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public TokenEntity toEntity(TokenProxy proxy){
        return TokenEntity.builder()
                .id(proxy.getId())
                .value(proxy.getValue())
                .subject(proxy.getSubject())
                .issuer(proxy.getIssuer())
                .audience(proxy.getAudience())
                .expiresAt(proxy.getExpiresAt())
                .issuedAt(proxy.getIssuedAt())
                .scopes(proxy.getScopes())
                .revoked(proxy.getRevoked())
                .build();
    }

    public TokenProxy toProxy(TokenEntity entity){
        return TokenProxy.builder()
                .id(entity.getId())
                .value(entity.getValue())
                .subject(entity.getSubject())
                .issuer(entity.getIssuer())
                .audience(entity.getAudience())
                .expiresAt(entity.getExpiresAt())
                .issuedAt(entity.getIssuedAt())
                .scopes(entity.getScopes())
                .revoked(entity.getRevoked())
                .build();
    }

}
