package com.example.userservice.client;

import com.example.userservice.model.JwtRequest;
import com.example.userservice.model.JwtResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthServiceClient {

    @Value("${auth-service.url}")
    private String authServiceUrl;

    private final RestTemplate restTemplate;

    public AuthServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JwtResponse login(JwtRequest request) {
        return restTemplate.postForObject(authServiceUrl + "/login", request, JwtResponse.class);
    }
}
