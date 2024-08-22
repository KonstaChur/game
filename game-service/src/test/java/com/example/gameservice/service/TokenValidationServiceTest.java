package com.example.gameservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TokenValidationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TokenValidationService tokenValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateToken_ShouldReturnTrue_WhenTokenIsValid() {
        // Arrange
        String token = "validToken";
        String user = "user1";
        String url = "http://localhost/validate?user=user1&token=validToken";

        // Mock the RestTemplate's response
        when(restTemplate.getForEntity(anyString(), any(Class.class)))
                .thenReturn(ResponseEntity.ok(true));

        // Act
        boolean result = tokenValidationService.validateToken(token, user);

        // Assert
        assertTrue(result);
    }

    @Test
    void validateToken_ShouldReturnFalse_WhenTokenIsInvalid() {
        // Arrange
        String token = "invalidToken";
        String user = "user2";
        String url = "http://localhost/validate?user=user2&token=invalidToken";

        // Mock the RestTemplate's response
        when(restTemplate.getForEntity(anyString(), any(Class.class)))
                .thenReturn(ResponseEntity.ok(false));

        // Act
        boolean result = tokenValidationService.validateToken(token, user);

        // Assert
        assertFalse(result);
    }

    @Test
    void validateToken_ShouldHandleRestTemplateExceptions() {
        // Arrange
        String token = "anyToken";
        String user = "anyUser";
        String url = "http://localhost/validate?user=anyUser&token=anyToken";

        // Mock the RestTemplate to throw an exception
        when(restTemplate.getForEntity(anyString(), any(Class.class)))
                .thenThrow(new RuntimeException("Service unavailable"));

        // Act
        boolean result = tokenValidationService.validateToken(token, user);

        // Assert
        assertFalse(result);
    }

}
