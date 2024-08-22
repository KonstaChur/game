//package com.example.gameservice.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//import com.example.gameservice.command.ICommand;
//import com.example.gameservice.context.CommandContext;
//import com.example.gameservice.exception.ValidationException;
//import com.example.gameservice.messaging.Producer;
//import com.example.gameservice.validation.CommandValidator;
//import com.fasterxml.jackson.databind.JsonNode;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class CommandExecutionHandlerTest {
//
//    @Mock
//    private Map<String, ICommand> commandBeans;
//    @Mock
//    private Map<String, CommandValidator> commandValidators;
//    @Mock
//    private Producer producer;
//    @Mock
//    private CommandHandler nextHandler;
//    @Mock
//    private ICommand command;
//    @Mock
//    private CommandValidator validator;
//    @Mock
//    private CommandContext context;
//    @Mock
//    private JsonNode commandsNode;
//    @Mock
//    private JsonNode commandNode;
//    @Mock
//    private JsonNode dataNode;
//
//    private CommandExecutionHandler handler;
//
//    @BeforeEach
//    void setUp() {
//        handler = new CommandExecutionHandler(commandBeans, commandValidators, producer);
//        handler.setNext(nextHandler);
//    }
//
//    @Test
//    void handle_shouldExecuteCommands() throws ValidationException {
//        when(context.getCommandsNode()).thenReturn(commandsNode);
//        when(commandsNode.iterator()).thenReturn(new Iterator<>() {
//            @Override
//            public boolean hasNext() {
//                return true;
//            }
//
//            @Override
//            public JsonNode next() {
//                return commandNode;
//            }
//        });
//        when(commandNode.path("name")).thenReturn(mock(JsonNode.class));
//        when(commandNode.path("data")).thenReturn(dataNode);
//        when(commandBeans.get(any())).thenReturn(command);
//        when(commandValidators.get(any())).thenReturn(validator);
//        when(validator.validate(any())).thenReturn(true);
//        doNothing().when(command).execute();
//
//        handler.handle(context);
//
//        verify(nextHandler).handle(context);
//    }
//
//    // ... other tests
//}
