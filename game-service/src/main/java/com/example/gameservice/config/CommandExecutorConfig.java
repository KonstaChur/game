package com.example.gameservice.config;

import com.example.gameservice.command.ICommand;
import com.example.gameservice.command.message.MessageCommand;
import com.example.gameservice.messaging.Producer;
import com.example.gameservice.service.*;
import com.example.gameservice.state.ExecutionState;
import com.example.gameservice.validation.CommandValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CommandExecutorConfig {

    @Bean
    public CommandExecutorService commandExecutorService(
            Map<String, ICommand> commandBeans,
            Map<String, CommandValidator> commandValidators,
            Producer producer,
            ObjectMapper objectMapper,
            ExecutionState executionState,
            MessageCommand messageCommand,
            TokenValidationService tokenValidationService
    ) {
        CommandHandler routingKeyHandler = new RoutingKeyHandler();
        CommandHandler tokenValidationHandler = new TokenValidationHandler(tokenValidationService);
        CommandHandler commandExecutionHandler = new CommandExecutionHandler(commandBeans, commandValidators, producer);

        routingKeyHandler.setNext(tokenValidationHandler);
        tokenValidationHandler.setNext(commandExecutionHandler);

        return new CommandExecutorService(objectMapper, executionState, messageCommand, routingKeyHandler);
    }
}
