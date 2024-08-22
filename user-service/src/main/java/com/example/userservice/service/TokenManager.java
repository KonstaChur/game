package com.example.userservice.service;

import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    private final AuthService authService;
    private String token;

    public TokenManager(AuthService authService) {
        this.authService = authService;
    }

    public String getToken() {
        if (token == null || isTokenExpired()) {
            token = authService.authenticate();
        }
        return token;
    }

    //TODO написать логику проверки истечения срока действия токена
    public boolean isTokenExpired() {
        return false;
    }
}
