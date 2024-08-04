//package com.example.gameservice.service;
//
//import com.example.gameservice.DTO.CommandRequest;
//import com.example.gameservice.command.ICommand;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.function.Supplier;
//
//@Service
//public class CommandProcessorService {
//
//    private final Map<String, Supplier<ICommand>> commandRegistry;
//
//    public CommandProcessorService(Map<String, Supplier<ICommand>> commandRegistry) {
//        this.commandRegistry = commandRegistry;
//    }
//    //    паттерн "Реестр команд"
//    public String processCommand(CommandRequest commandRequest) {
//        Map.Entry<String, Object> entry = commandRequest.getCommand().entrySet().iterator().next();
//        String commandName = entry.getKey().toLowerCase();
//
//        Supplier<ICommand> commandSupplier = commandRegistry.get(commandName);
//
//        if (commandSupplier != null) {
//            ICommand command = commandSupplier.get();
//            return command.execute();
//        } else {
//            return "Unknown command";
//        }
//    }
//}