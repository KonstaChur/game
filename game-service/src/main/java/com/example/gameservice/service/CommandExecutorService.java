package com.example.gameservice.service;

import com.example.gameservice.command.ICommand;
import com.example.gameservice.command.message.MessageCommand;
import com.example.gameservice.context.CommandContext;
import com.example.gameservice.exception.ValidationException;
import com.example.gameservice.messaging.Producer;
import com.example.gameservice.state.ExecutionState;
import com.example.gameservice.validation.CommandValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandExecutorService {

    private final ObjectMapper objectMapper;
    private final ExecutionState executionState;
    private final MessageCommand messageCommand;
    private final CommandHandler commandHandlerChain;

    public void executeCommands(String token, String json) throws JsonProcessingException {
        long threadId = Thread.currentThread().getId();
        log.info("Выполнение начато в потоке: {}", threadId);

        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode commandsNode = rootNode.path("commands");
        JsonNode data = commandsNode.path("data");

        CommandContext context = new CommandContext();
        context.setToken(token);
        context.setCommandsNode(commandsNode);
        context.setThreadId(threadId);
        context.setCommandData(data.asText());

        commandHandlerChain.handle(context);

        if (executionState.isStopExecution()) {
            log.info("Остановка выполнения в потоке: {}", threadId);
            return;
        }

        executionState.clear();
        log.info("Все команды успешно выполнены в потоке: {}", threadId);
        messageCommand.execute();
    }
}
