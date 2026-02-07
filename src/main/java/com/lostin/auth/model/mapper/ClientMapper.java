package com.lostin.auth.model.mapper;

import com.lostin.auth.model.entity.ClientEntity;
import com.lostin.auth.model.proxy.ClientProxy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public ClientEntity toEntity(ClientProxy proxy){
        return ClientEntity.builder()
                .id(proxy.getId())
                .secretHash(proxy.getSecretHash())
                .redirectUri(String.join(" ", proxy.getRedirectUris()))
                .type(proxy.getType())
                .requirePkce(proxy.getRequirePkce())
                .status(proxy.getStatus())
                .trusted(proxy.getTrusted())
                .allowedScopes(String.join(" ", proxy.getAllowedScopes()))
                .userId(proxy.getUserId())
                .name(proxy.getName())
                .description(proxy.getDescription())
                .logoUri(proxy.getLogoUri())
                .createdAt(proxy.getCreatedAt())
                .build();
    }

    public ClientProxy toProxy(ClientEntity entity){
        return ClientProxy.builder()
                .id(entity.getId())
                .secretHash(entity.getSecretHash())
                .redirectUris((Arrays.stream(entity.getRedirectUri().split(" ")).collect(Collectors.toSet())))
                .type(entity.getType())
                .requirePkce(entity.getRequirePkce())
                .status(entity.getStatus())
                .trusted(entity.getTrusted())
                .allowedScopes(Arrays.stream(entity.getAllowedScopes().split(" ")).collect(Collectors.toSet()))
                .userId(entity.getUserId())
                .name(entity.getName())
                .description(entity.getDescription())
                .logoUri(entity.getLogoUri())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
