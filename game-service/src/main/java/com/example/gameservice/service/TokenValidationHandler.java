package com.example.gameservice.service;

import com.example.gameservice.context.CommandContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TokenValidationHandler implements CommandHandler {

    private final TokenValidationService tokenValidationService;
    private CommandHandler nextHandler;

    @Override
    public void setNext(CommandHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(CommandContext context) {
        if (!tokenValidationService.validateToken(context.getToken(), context.getRoutingKey())) {
            log.error("Неверный токен");
            context.setStopExecution(true);
        } else if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }
}
