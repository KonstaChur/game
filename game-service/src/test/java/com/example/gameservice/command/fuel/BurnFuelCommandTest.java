package com.example.gameservice.command.fuel;

import com.example.gameservice.DTO.FuelDto;
import com.example.gameservice.context.CommandContext;
import com.example.gameservice.service.CommandContextHolder;
import com.example.gameservice.service.GameObject;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class BurnFuelCommandTest {

    @Mock
    private GameObject gameObject;

    @InjectMocks
    private BurnFuelCommand burnFuelCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_commandContextIsNull() {
        // Setup
        CommandContextHolder.setContext(null);

        // Execute
        burnFuelCommand.execute();

        // Verify
        verify(gameObject, never()).setCurrentFuel(anyString());
    }

    @Test
    void testExecute_invalidJson() {
        // Setup
        CommandContext commandContext = mock(CommandContext.class);
        CommandContextHolder.setContext(commandContext);
        when(commandContext.getCommandData()).thenReturn("{invalid_json");

        // Execute
        burnFuelCommand.execute();

        // Verify
        verify(gameObject, never()).setCurrentFuel(anyString());
    }

    @Test
    void testExecute_validJson() {
        // Setup
        FuelDto fuelDto = new FuelDto("user1", "game1", 100.0, 10.0);
        String json = new Gson().toJson(fuelDto);

        CommandContext commandContext = mock(CommandContext.class);
        CommandContextHolder.setContext(commandContext);
        when(commandContext.getCommandData()).thenReturn(json);

        // Execute
        burnFuelCommand.execute();

        // Verify
        ArgumentCaptor<String> fuelCaptor = ArgumentCaptor.forClass(String.class);
        verify(gameObject).setCurrentFuel(fuelCaptor.capture());

        // Assertions
        assertEquals("90.0", fuelCaptor.getValue());  // 100.0 - 10.0 = 90.0
    }

    @Test
    void testExecute_fuelDtoIsNull() {
        // Setup
        FuelDto fuelDto = new FuelDto("user1", "game1", null, null); // Set null values
        String json = new Gson().toJson(fuelDto);

        CommandContext commandContext = mock(CommandContext.class);
        CommandContextHolder.setContext(commandContext);
        when(commandContext.getCommandData()).thenReturn(json);

        // Execute
        burnFuelCommand.execute();

        // Verify
        verify(gameObject, never()).setCurrentFuel(anyString());
    }


}
