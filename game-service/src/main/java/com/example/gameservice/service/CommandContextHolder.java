package com.example.gameservice.service;

import com.example.gameservice.context.CommandContext;

public class CommandContextHolder {

    private static final ThreadLocal<CommandContext> contextHolder = new ThreadLocal<>();

    public static void setContext(CommandContext context) {
        contextHolder.set(context);
    }

    public static CommandContext getContext() {
        return contextHolder.get();
    }


    public static void clearContext() {
        contextHolder.remove();
    }
}
