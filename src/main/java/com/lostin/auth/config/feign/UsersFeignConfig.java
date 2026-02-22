package com.lostin.auth.config.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class UsersFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper mapper) {
        return new UsersClientErrorDecoder(mapper);
    }
}
