package com.example.gameservice.service;

import com.example.gameservice.command.ICommand;
import com.example.gameservice.context.CommandContext;
import com.example.gameservice.exception.ValidationException;
import com.example.gameservice.messaging.Producer;
import com.example.gameservice.validation.CommandValidator;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CommandExecutionHandler implements CommandHandler {

    private final Map<String, ICommand> commandBeans;
    private final Map<String, CommandValidator> commandValidators;
    private final Producer producer;

    private CommandHandler nextHandler;

    @Override
    public void setNext(CommandHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(CommandContext context) {
        JsonNode commandsNode = context.getCommandsNode();

        for (JsonNode commandNode : commandsNode) {
            if (context.isStopExecution()) {
                log.info("Остановка выполнения в потоке: {}", context.getThreadId());
                return;
            }

            String commandName = commandNode.path("name").asText();
            JsonNode dataNode = commandNode.path("data");

            context.setCommandData(dataNode.toString());

            ICommand command = commandBeans.get(commandName);
            if (command == null) {
                handleUnknownCommandError(context, commandName);
                return;
            }

            if (!validateCommand(context, commandName, dataNode)) {
                return;
            }

            CommandExecutorAdapter adapter = new CommandExecutorAdapter(command, context);
            try {
                adapter.execute();
            } catch (RuntimeException e) {
                handleCommandExecutionError(context, commandName, e);
                return;
            }
        }

        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    private boolean validateCommand(CommandContext context, String commandName, JsonNode dataNode) {
        CommandValidator validator = commandValidators.get(commandName + "validator");
        if (validator != null) {
            try {
                validator.validate(dataNode);
                return true;
            } catch (ValidationException e) {
                sendMessageToRabbit(context, "Ошибка валидации команды: " + commandName + " - " + e.getMessage());
            }
        } else {
            sendMessageToRabbit(context, "Нет валидатора для команды: " + commandName);
        }
        context.setStopExecution(true);
        return false;
    }

    private void handleUnknownCommandError(CommandContext context, String commandName) {
        log.error("Неизвестная команда '{}' в потоке: {}", commandName, context.getThreadId());
        sendMessageToRabbit(context, "Неизвестная команда: " + commandName);
        context.setStopExecution(true);
    }

    private void handleCommandExecutionError(CommandContext context, String commandName, RuntimeException e) {
        log.error("Ошибка выполнения команды '{}' в потоке: {} - {}", commandName, context.getThreadId(), e.getMessage());
        sendMessageToRabbit(context, "Ошибка выполнения команды: " + commandName + " - " + e.getMessage());
        context.setStopExecution(true);
    }

    private void sendMessageToRabbit(CommandContext context, String errorMessage) {
        log.error(errorMessage);
        producer.sendMessage(errorMessage, context.getRoutingKey());
    }
}
