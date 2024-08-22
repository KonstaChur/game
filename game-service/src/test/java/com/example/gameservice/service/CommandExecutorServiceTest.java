package com.example.gameservice.service;

import com.example.gameservice.command.message.MessageCommand;
import com.example.gameservice.context.CommandContext;
import com.example.gameservice.state.ExecutionState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommandExecutorServiceTest {

    private ObjectMapper objectMapper;
    private ExecutionState executionState;
    private MessageCommand messageCommand;
    private CommandHandler commandHandlerChain;
    private CommandExecutorService commandExecutorService;

    @BeforeEach
    public void setUp() {
        objectMapper = mock(ObjectMapper.class);
        executionState = mock(ExecutionState.class);
        messageCommand = mock(MessageCommand.class);
        commandHandlerChain = mock(CommandHandler.class);
        commandExecutorService = new CommandExecutorService(objectMapper, executionState, messageCommand, commandHandlerChain);
    }

    @Test
    public void testExecuteCommandsSuccess() throws IOException {
        String token = "test-token";
        String json = "{\"commands\": {\"data\": \"command-data\"}}";
        JsonNode rootNode = mock(JsonNode.class);
        JsonNode commandsNode = mock(JsonNode.class);
        JsonNode dataNode = mock(JsonNode.class);

        when(objectMapper.readTree(json)).thenReturn(rootNode);
        when(rootNode.path("commands")).thenReturn(commandsNode);
        when(commandsNode.path("data")).thenReturn(dataNode);
        when(dataNode.asText()).thenReturn("command-data");
        when(executionState.isStopExecution()).thenReturn(false);

        doNothing().when(commandHandlerChain).handle(any(CommandContext.class));
        doNothing().when(messageCommand).execute();

        commandExecutorService.executeCommands(token, json);

        // Проверка взаимодействий
        verify(objectMapper).readTree(json);
        verify(commandHandlerChain).handle(any(CommandContext.class));
        verify(messageCommand).execute();
        verify(executionState).clear();
    }
}
