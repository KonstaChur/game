package com.example.gameservice.service;

import com.example.gameservice.command.ICommand;
import com.example.gameservice.context.CommandContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class CommandExecutorAdapterTest {

    private ICommand command;
    private CommandContext commandContext;
    private CommandExecutorAdapter commandExecutorAdapter;

    @BeforeEach
    public void setUp() {
        command = mock(ICommand.class);
        commandContext = mock(CommandContext.class);
        commandExecutorAdapter = new CommandExecutorAdapter(command, commandContext);
    }

    @Test
    public void testExecute() {
        // Mock static methods for CommandContextHolder
        try (var mockStatic = mockStatic(CommandContextHolder.class)) {
            commandExecutorAdapter.execute();

            // Verify that CommandContextHolder methods were called
            mockStatic.verify(() -> CommandContextHolder.setContext(commandContext));
            mockStatic.verify(() -> CommandContextHolder.clearContext());

            // Verify that command.execute() was called
            verify(command, times(1)).execute();
        }
    }
}
