package com.example.gameservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TokenValidationService {

    @Value("${auth-service.url}")
    private String authServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    public boolean validateToken(String token, String user) {
        String url = String.format(authServiceUrl+"/validate?user=%s&token=%s", user, token);
        try {
            ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
            return response != null && Boolean.TRUE.equals(response.getBody());
        } catch (Exception e) {
            return false;
        }
    }

}
