package com.lostin.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LostInAuthApplication {

    public static void main(String[] args) {
//        Dotenv dotenv = Dotenv.load();
        System.setProperty("AUTH_APP_BASE_URL", "http://localhost:8080");
        System.setProperty("AUTH_ACCESS_TOKEN_TTL_SECONDS", "600");
        System.setProperty("AUTH_REFRESH_TOKEN_TTL_HOURS", "10");
        System.setProperty("AUTH_RSA_KEYS_LOCAL_STORAGE_PATH", "nimbusgen-jwk-rsa.json");

        SpringApplication.run(LostInAuthApplication.class, args);
    }

}
