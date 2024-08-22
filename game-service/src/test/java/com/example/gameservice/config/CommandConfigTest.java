package com.example.gameservice.config;

import com.example.gameservice.command.ICommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommandConfigTest {

    private CommandConfig commandConfig;

    private ICommand command1;
    private ICommand command2;

    @BeforeEach
    public void setUp() {
        commandConfig = new CommandConfig(); // Создаем экземпляр CommandConfig
        command1 = Mockito.mock(ICommand.class);
        command2 = Mockito.mock(ICommand.class);
    }

    @Test
    public void testCommandRegistry() {

        Map<String, ICommand> commandBeans = new HashMap<>();
        commandBeans.put("command1", command1);
        commandBeans.put("command2", command2);

        Map<String, Supplier<ICommand>> registry = commandConfig.commandRegistry(commandBeans);

        assertNotNull(registry);
        assertEquals(2, registry.size(), "The registry should contain exactly 2 commands");

        assertEquals(command1, registry.get("command1").get());
        assertEquals(command2, registry.get("command2").get());
    }
}
