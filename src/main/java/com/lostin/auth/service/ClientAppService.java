package com.lostin.auth.service;

import com.lostin.auth.request_response.client_service.request.CreateClientRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientAppService {

    public boolean createClientApp(CreateClientRequest request){
        return false;
    }

    public boolean deleteClientApp(String clientId){
        return false;
    }

    public boolean updateClientApp(String clientId){
        return false;
    }



}
