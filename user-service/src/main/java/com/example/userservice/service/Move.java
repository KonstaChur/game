package com.example.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class Move {

    private final RestTemplate restTemplate;
    private final String gameServiceUrl;
    private final TokenManager tokenManager;

    public Move(RestTemplate restTemplate,
                @Value("${game-service.url}") String gameServiceUrl,
                TokenManager tokenManager) {
        this.restTemplate = restTemplate;
        this.gameServiceUrl = gameServiceUrl;
        this.tokenManager = tokenManager;
    }

    public String executeCommands() throws IOException {
        String url = gameServiceUrl + "/execute";
        String commands;

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("command/moveFuel.json");
        commands = new String(inputStream.readAllBytes());

        HttpEntity<String> entity = new HttpEntity<>(commands, getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }


    private HttpHeaders getHeaders() {
        String token = tokenManager.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
