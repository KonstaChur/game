package com.example.gameservice.config;

import com.example.gameservice.command.ICommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
@ComponentScan(basePackages = "com.example.gameservice")
@Slf4j
public class CommandConfig {

    @Bean
    public Map<String, Supplier<ICommand>> commandRegistry(Map<String, ICommand> commandBeans) {
        Map<String, Supplier<ICommand>> registry = new HashMap<>();

        commandBeans.forEach((name, command) -> {
            String commandName = name;
            System.out.println(commandName);
            registry.put(commandName, () -> command);
        });

        return registry;
    }
}
