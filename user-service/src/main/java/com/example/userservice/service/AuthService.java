package com.example.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class AuthService {
    private final RestTemplate restTemplate;
    private final String authServiceUrl;
    private final String authServiceUser;
    private final String authServicePassword;

    public AuthService(RestTemplate restTemplate,
                       @Value("${auth-service.url}") String authServiceUrl,
                       @Value("${auth-service.user}") String authServiceUser,
                       @Value("${auth-service.password}") String authServicePassword) {
        this.restTemplate = restTemplate;
        this.authServiceUrl = authServiceUrl;
        this.authServiceUser = authServiceUser;
        this.authServicePassword = authServicePassword;
    }

    public String authenticate() {
        String url = String.format("%s/authenticate?user=%s&password=%s", authServiceUrl, authServiceUser, authServicePassword);
        log.info("url = "+url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String test (){
        String url = String.format("%s/test", authServiceUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
