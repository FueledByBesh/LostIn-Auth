package com.lostin.auth.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Password {

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "argon2";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder(
                16,
                32,
                1,
                65536,
                3
        ));

        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

}
