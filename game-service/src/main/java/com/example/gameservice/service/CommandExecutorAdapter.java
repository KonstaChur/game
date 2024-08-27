package com.example.gameservice.service;

import com.example.gameservice.command.ICommand;
import com.example.gameservice.context.CommandContext;

public class CommandExecutorAdapter {

    private final ICommand command;
    private final CommandContext commandContext;

    public CommandExecutorAdapter(ICommand command, CommandContext commandContext) {
        this.command = command;
        this.commandContext = commandContext;
    }

    public void execute() {
        CommandContextHolder.setContext(commandContext);
        try {
            command.execute();
        } finally {
            CommandContextHolder.clearContext();
        }
    }
}
