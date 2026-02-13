package com.lostin.auth.service;

import com.lostin.auth.model.core.user.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SSOSessionService {

    public UUID saveSession(UserId userId){
        return UUID.randomUUID();
    }

}
