package com.lostin.auth.model.mapper;

import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.model.entity.UserCredentialsEntity;
import com.lostin.auth.model.proxy.UserCredentialsProxy;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsMapper {

    public UserCredentialsProxy toProxy(UserCredentialsEntity entity){
        return UserCredentialsProxy.builder()
                .userId(UserId.validated(entity.getUserId()))
                .passwordHash(entity.getPasswordHash())
                .build();
    }

    public UserCredentialsEntity toEntity(UserCredentialsProxy proxy){
        return UserCredentialsEntity.builder()
                .userId(proxy.getUserId().value())
                .passwordHash(proxy.getPasswordHash())
                .build();
    }
}
