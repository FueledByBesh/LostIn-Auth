package com.lostin.auth.config.beans;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.PropertyNamingStrategies;

@Configuration
public class JacksonConfig {

    @Bean
    public JsonMapperBuilderCustomizer mapperCustomizer(){
        return builder ->{
            builder.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
            builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        };
    }

}
