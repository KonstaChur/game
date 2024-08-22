package com.example.gameservice.command.fuel;

import com.example.gameservice.DTO.FuelDto;
import com.example.gameservice.context.CommandContext;
import com.example.gameservice.exception.CommandException;
import com.example.gameservice.service.CommandContextHolder;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckFuelCommandTest {

    private CheckFuelCommand checkFuelCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        checkFuelCommand = new CheckFuelCommand();
    }

    @Test
    void execute_ShouldPass_WhenSufficientFuel() {
        // Arrange
        FuelDto fuelDto = new FuelDto();
        fuelDto.setCurrentFuel(100.0);
        fuelDto.setFuelConsumption(20.0);
        String json = new Gson().toJson(fuelDto);

        CommandContext commandContext = mock(CommandContext.class);
        when(commandContext.getCommandData()).thenReturn(json);

        try (MockedStatic<CommandContextHolder> commandContextHolderMock = Mockito.mockStatic(CommandContextHolder.class)) {
            commandContextHolderMock.when(CommandContextHolder::getContext).thenReturn(commandContext);

            // Act
            checkFuelCommand.execute();

            // Assert
            // No exception means test passed
        }
    }

    @Test
    void execute_ShouldThrowCommandException_WhenInsufficientFuel() {
        // Arrange
        FuelDto fuelDto = new FuelDto();
        fuelDto.setCurrentFuel(10.0);
        fuelDto.setFuelConsumption(20.0);
        String json = new Gson().toJson(fuelDto);

        CommandContext commandContext = mock(CommandContext.class);
        when(commandContext.getCommandData()).thenReturn(json);

        try (MockedStatic<CommandContextHolder> commandContextHolderMock = Mockito.mockStatic(CommandContextHolder.class)) {
            commandContextHolderMock.when(CommandContextHolder::getContext).thenReturn(commandContext);

            // Act & Assert
           RuntimeException thrown = assertThrows(RuntimeException.class, checkFuelCommand::execute);
            assertEquals("Нехватка топлива", thrown.getMessage());
        }
    }

    @Test
    void execute_ShouldThrowIllegalStateException_WhenContextIsNull() {
        // Arrange
        try (MockedStatic<CommandContextHolder> commandContextHolderMock = Mockito.mockStatic(CommandContextHolder.class)) {
            commandContextHolderMock.when(CommandContextHolder::getContext).thenReturn(null);

            // Act & Assert
            IllegalStateException thrown = assertThrows(IllegalStateException.class, checkFuelCommand::execute);
            assertEquals("Command context is not set", thrown.getMessage());
        }
    }
}
