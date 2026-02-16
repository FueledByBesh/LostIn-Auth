package com.lostin.auth.service;

import com.lostin.auth.model.core.client.ClientStatus;
import com.lostin.auth.model.proxy.ClientProxy;
import com.lostin.auth.repository.ClientRepository;
import com.lostin.auth.request_response.client_service.request.CreateClientRequest;
import com.lostin.auth.request_response.client_service.response.CreateClientResponse;
import com.lostin.auth.util.Hasher;
import com.lostin.auth.util.OpaqueTokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    /* Todo:
        In future validate client,
        for now just passing them without validation just for test
     */
    public CreateClientResponse createClientApp(CreateClientRequest request){
        String secret = OpaqueTokenGenerator.generateOpaqueToken();
        String hashedSecret = Hasher.sha256(secret);
        ClientProxy client = ClientProxy.builder()
                .secretHash(hashedSecret)
                .redirectUris(request.redirectUris())
                .type(request.type())
                .requirePkce(request.requirePkce())
                .trusted(request.trusted())
                .status(ClientStatus.ACTIVE)
                .allowedScopes(request.allowedScopes())
                .userId(request.userId())
                .name(request.name())
                .description(request.description())
                .logoUri(request.logoUri())
                .build();

        client = clientRepository.save(client);
        System.out.println(client.getId());
        System.out.println(secret);
        return CreateClientResponse.builder()
                .clientId(client.getId())
                .clientSecret(secret)
                .build();
    }

    public boolean deleteClientApp(String clientId){
        return false;
    }

    public boolean updateClientApp(String clientId){
        return false;
    }



}
