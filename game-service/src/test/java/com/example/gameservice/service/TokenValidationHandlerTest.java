package com.example.gameservice.service;

import com.example.gameservice.context.CommandContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenValidationHandlerTest {

    private TokenValidationHandler tokenValidationHandler;
    private TokenValidationService tokenValidationService;
    private CommandHandler nextHandler;
    private CommandContext context;
    private Logger logger;

    @BeforeEach
    void setUp() {
        tokenValidationService = Mockito.mock(TokenValidationService.class);
        tokenValidationHandler = new TokenValidationHandler(tokenValidationService);
        nextHandler = Mockito.mock(CommandHandler.class);
        context = Mockito.mock(CommandContext.class);
        logger = mock(Logger.class);
    }

    @Test
    void testHandle_WithValidToken() {
        // Arrange
        when(context.getToken()).thenReturn("validToken");
        when(context.getRoutingKey()).thenReturn("123");
        when(tokenValidationService.validateToken("validToken", "123")).thenReturn(true);

        tokenValidationHandler.setNext(nextHandler);

        // Act
        tokenValidationHandler.handle(context);

        // Assert
        verify(tokenValidationService).validateToken("validToken", "123");
        verify(nextHandler).handle(context);
        verify(context, never()).setStopExecution(true);
    }

    @Test
    void testHandle_WithInvalidToken() {
        // Arrange
        when(context.getToken()).thenReturn("invalidToken");
        when(context.getRoutingKey()).thenReturn("123");
        when(tokenValidationService.validateToken("invalidToken", "123")).thenReturn(false);

        tokenValidationHandler.setNext(nextHandler);

        // Act
        tokenValidationHandler.handle(context);

        // Assert
        verify(tokenValidationService).validateToken("invalidToken", "123");
        verify(context).setStopExecution(true);
        verify(nextHandler, never()).handle(context);
    }

    @Test
    void testHandle_NoNextHandler_WithValidToken() {
        // Arrange
        when(context.getToken()).thenReturn("validToken");
        when(context.getRoutingKey()).thenReturn("123");
        when(tokenValidationService.validateToken("validToken", "123")).thenReturn(true);

        // Act
        tokenValidationHandler.handle(context);

        // Assert
        verify(tokenValidationService).validateToken("validToken", "123");
        verify(context, never()).setStopExecution(true);
        verify(nextHandler, never()).handle(context);
    }

    @Test
    void testHandle_NoNextHandler_WithInvalidToken() {
        // Arrange
        when(context.getToken()).thenReturn("invalidToken");
        when(context.getRoutingKey()).thenReturn("123");
        when(tokenValidationService.validateToken("invalidToken", "123")).thenReturn(false);

        // Act
        tokenValidationHandler.handle(context);

        // Assert
        verify(tokenValidationService).validateToken("invalidToken", "123");
        verify(context).setStopExecution(true);
        verify(nextHandler, never()).handle(context);
    }
}
