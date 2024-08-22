package com.example.gameservice.service;

import com.example.gameservice.context.CommandContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoutingKeyHandler implements CommandHandler {

    private CommandHandler nextHandler;

    @Override
    public void setNext(CommandHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(CommandContext context) {
        JsonNode commandsNode = context.getCommandsNode();
        JsonNode firstCommandDataNode = commandsNode.path(0).path("data");
        String routingKey = firstCommandDataNode.path("id_user").asText("");

        context.setRoutingKey(routingKey);

        if (routingKey.isEmpty()) {
            log.error("idUser равен null");
            context.setStopExecution(true);
        } else if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }
}
