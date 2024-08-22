package com.example.userservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuthService authService;

    @Value("${auth-service.url}")
    private String authServiceUrl = "http://authservice";

    @Value("${auth-service.user}")
    private String authServiceUser = "testUser";

    @Value("${auth-service.password}")
    private String authServicePassword = "testPassword";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(restTemplate, authServiceUrl, authServiceUser, authServicePassword);
    }

    @Test
    public void testAuthenticate_Success() {
        String url = String.format("%s/authenticate?user=%s&password=%s", authServiceUrl, authServiceUser, authServicePassword);
        String expectedResponse = "authenticatedToken";

        when(restTemplate.getForEntity(url, String.class)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        String result = authService.authenticate();

        assertEquals(expectedResponse, result);
        verify(restTemplate, times(1)).getForEntity(url, String.class);
    }

    @Test
    public void testTest_Success() {
        String url = String.format("%s/test", authServiceUrl);
        String expectedResponse = "testSuccess";

        when(restTemplate.getForEntity(url, String.class)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        String result = authService.test();

        assertEquals(expectedResponse, result);
        verify(restTemplate, times(1)).getForEntity(url, String.class);
    }

    @Test
    public void testAuthenticate_Failure() {
        String url = String.format("%s/authenticate?user=%s&password=%s", authServiceUrl, authServiceUser, authServicePassword);
        when(restTemplate.getForEntity(url, String.class)).thenReturn(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

        String result = authService.authenticate();

        assertEquals(null, result);
        verify(restTemplate, times(1)).getForEntity(url, String.class);
    }
}
