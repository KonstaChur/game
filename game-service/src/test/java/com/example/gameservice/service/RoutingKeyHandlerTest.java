package com.example.gameservice.service;

import com.example.gameservice.context.CommandContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoutingKeyHandlerTest {

    private RoutingKeyHandler routingKeyHandler;
    private CommandHandler nextHandler;
    private CommandContext context;
    private ObjectMapper objectMapper;
    private Logger logger;

    @BeforeEach
    void setUp() {
        routingKeyHandler = new RoutingKeyHandler();
        nextHandler = Mockito.mock(CommandHandler.class);
        context = Mockito.mock(CommandContext.class);
        objectMapper = new ObjectMapper();
        logger = mock(Logger.class);
    }

    @Test
    void testHandle_WithValidIdUser() throws Exception {
        // Arrange
        String json = "[{\"data\": {\"id_user\": \"123\"}}]";
        JsonNode commandsNode = objectMapper.readTree(json);

        when(context.getCommandsNode()).thenReturn(commandsNode);

        routingKeyHandler.setNext(nextHandler);

        // Act
        routingKeyHandler.handle(context);

        // Assert
        verify(context).setRoutingKey("123");
        verify(nextHandler).handle(context);
        verify(context, never()).setStopExecution(true);
    }

    @Test
    void testHandle_WithEmptyIdUser() throws Exception {
        // Arrange
        String json = "[{\"data\": {\"id_user\": \"\"}}]";
        JsonNode commandsNode = objectMapper.readTree(json);

        when(context.getCommandsNode()).thenReturn(commandsNode);

        routingKeyHandler.setNext(nextHandler);

        // Act
        routingKeyHandler.handle(context);

        // Assert
        verify(context).setRoutingKey("");
        verify(context).setStopExecution(true);
        verify(nextHandler, never()).handle(context);
    }

    @Test
    void testHandle_WithMissingIdUser() throws Exception {
        // Arrange
        String json = "[{\"data\": {}}]";
        JsonNode commandsNode = objectMapper.readTree(json);

        when(context.getCommandsNode()).thenReturn(commandsNode);

        routingKeyHandler.setNext(nextHandler);

        // Act
        routingKeyHandler.handle(context);

        // Assert
        verify(context).setRoutingKey("");
        verify(context).setStopExecution(true);
        verify(nextHandler, never()).handle(context);
    }

    @Test
    void testHandle_NoNextHandler() throws Exception {
        // Arrange
        String json = "[{\"data\": {\"id_user\": \"123\"}}]";
        JsonNode commandsNode = objectMapper.readTree(json);

        when(context.getCommandsNode()).thenReturn(commandsNode);

        // Act
        routingKeyHandler.handle(context);

        // Assert
        verify(context).setRoutingKey("123");
        verify(context, never()).setStopExecution(true);
        verify(nextHandler, never()).handle(context);
    }
}
