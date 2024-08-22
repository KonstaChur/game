package com.example.authservice.config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

import static org.mockito.Mockito.*;

class WebConfigTest {

    private WebConfig webConfig;
    private CorsRegistry corsRegistry;
    private CorsRegistration corsRegistration;

    @BeforeEach
    void setUp() {
        webConfig = new WebConfig();
        corsRegistry = mock(CorsRegistry.class);
        corsRegistration = mock(CorsRegistration.class);

        // Настраиваем цепочку вызовов методов на CorsRegistration
        when(corsRegistry.addMapping("/**")).thenReturn(corsRegistration);
        when(corsRegistration.allowedOrigins(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders(anyString())).thenReturn(corsRegistration);
    }

    @Test
    void addCorsMappings_shouldAddCorsMapping() {
        // Act
        webConfig.addCorsMappings(corsRegistry);

        // Assert
        verify(corsRegistry).addMapping("/**");
        verify(corsRegistration).allowedOrigins("http://localhost:8088");
        verify(corsRegistration).allowedMethods("*");
        verify(corsRegistration).allowedHeaders("*");
    }
}