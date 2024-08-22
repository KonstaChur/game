package com.example.gameservice.config;

import com.example.gameservice.command.ICommand;
import com.example.gameservice.command.message.MessageCommand;
import com.example.gameservice.messaging.Producer;
import com.example.gameservice.service.CommandExecutorService;
import com.example.gameservice.service.RoutingKeyHandler;
import com.example.gameservice.service.TokenValidationHandler;
import com.example.gameservice.service.CommandExecutionHandler;
import com.example.gameservice.service.TokenValidationService;
import com.example.gameservice.state.ExecutionState;
import com.example.gameservice.validation.CommandValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig(CommandExecutorConfigTest.ConfigTest.class)
public class CommandExecutorConfigTest {

    @Mock
    private Map<String, ICommand> commandBeans;

    @Mock
    private Map<String, CommandValidator> commandValidators;

    @Mock
    private Producer producer;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ExecutionState executionState;

    @Mock
    private MessageCommand messageCommand;

    @Mock
    private TokenValidationService tokenValidationService;

    @InjectMocks
    private CommandExecutorConfig config;

    @Test
    public void testCommandExecutorServiceBeanCreation() {
        CommandExecutorService commandExecutorService = config.commandExecutorService(
                commandBeans,
                commandValidators,
                producer,
                objectMapper,
                executionState,
                messageCommand,
                tokenValidationService
        );

        assertNotNull(commandExecutorService, "CommandExecutorService should not be null");

    }

    @Configuration
    static class ConfigTest {
        @Bean
        public CommandExecutorConfig commandExecutorConfig() {
            return new CommandExecutorConfig();
        }
    }
}
