package com.example.gameservice.service;

import com.example.gameservice.context.CommandContext;

public interface CommandHandler {
    void setNext(CommandHandler nextHandler);
    void handle(CommandContext context);
}
