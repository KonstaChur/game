package com.example.gameservice.service;

import com.example.gameservice.command.ICommand;
import com.example.gameservice.context.CommandContext;
import com.example.gameservice.exception.ValidationException;
import com.example.gameservice.messaging.Producer;
import com.example.gameservice.validation.CommandValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class CommandExecutorService {

    @Autowired
    private Map<String, ICommand> commandBeans;

    @Autowired
    private Map<String, CommandValidator> commandValidators;

    @Autowired
    private CommandContext commandContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Producer producer;

    @PostConstruct
    public void logRegisteredCommands() {
        log.info("Registered commands:");
        commandBeans.forEach((name, command) -> log.info("Command name: {}", name));
    }

    String routingKey = "";

    public void executeCommands(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode commandsNode = rootNode.get("commands");
        String idUser = commandsNode.get(0).get("data").get("id_user").asText();


        if (idUser!= null){
            routingKey = idUser;
        }else {
            String errorMessage = "idUser is null";
            log.error(errorMessage);
            sendMessageToRabbit(errorMessage);
            return;
        }

        for (JsonNode commandNode : commandsNode) {
            String commandName = commandNode.get("name").asText().toLowerCase();
            JsonNode dataNode = commandNode.get("data");
            commandContext.setCommandData(commandNode.toString());
            ICommand command = commandBeans.get(commandName);
            CommandValidator validator = commandValidators.get(commandName + "validator");

            if (validator != null) {
                try {
                    validator.validate(dataNode);
                } catch (ValidationException e) {
                    String errorMessage = "Validation error in command: " + commandName + " - " + e.getMessage();
                    log.error(errorMessage);
                    sendMessageToRabbit(errorMessage);
                    break;
                }
            } else {
                String errorMessage = "No validator found for command: " + commandName;
                log.error(errorMessage);
                sendMessageToRabbit(errorMessage);
                break;
            }

            if (command != null) {
                try {
                    command.execute();
                } catch (RuntimeException e) {
                    log.error("Error executing command: " + commandName, e);
                    sendMessageToRabbit("Error executing command: " + commandName + " - " + e.getMessage());
                    break;
                }
            } else {
                String errorMessage = "Unknown command: " + commandName;
                log.error(errorMessage);
                sendMessageToRabbit(errorMessage);
                break;
            }
        }
    }

    private void sendMessageToRabbit(String errorMessage) {
        producer.sendMessage(errorMessage,routingKey);
    }
}
