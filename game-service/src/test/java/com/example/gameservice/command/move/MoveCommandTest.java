package com.example.gameservice.command.move;

import com.example.gameservice.DTO.MoveDto;
import com.example.gameservice.DTO.Position;
import com.example.gameservice.DTO.Velocity;
import com.example.gameservice.service.CommandContextHolder;
import com.example.gameservice.service.GameObject;
import com.example.gameservice.context.CommandContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.*;

public class MoveCommandTest {

    @Mock
    private GameObject gameObject;

    @InjectMocks
    private MoveCommand moveCommand;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_withValidData_updatesPosition() throws Exception {
        // Arrange
        CommandContext commandContext = mock(CommandContext.class);
        CommandContextHolder.setContext(commandContext);

        MoveDto moveDto = new MoveDto(
                "user1",
                "game1",
                new Position(10.0, 20.0),
                new Velocity(5.0, -3.0)
        );

        String json = objectMapper.writeValueAsString(moveDto);
        when(commandContext.getCommandData()).thenReturn(json);

        // Act
        moveCommand.execute();

        // Assert
        verify(gameObject).setId_game("game1");
        verify(gameObject).setId_user("user1");
        verify(gameObject).setPosition(new Position(15.0, 17.0));
    }

    @Test
    void testExecute_noCommandContext_logsError() {
        // Arrange
        CommandContextHolder.setContext(null);

        // Act
        moveCommand.execute();

        // Assert
        // Check logs or use a logger to verify that error message was logged
    }

    @Test
    void testExecute_noCommandData_logsWarning() {
        // Arrange
        CommandContext commandContext = mock(CommandContext.class);
        CommandContextHolder.setContext(commandContext);
        when(commandContext.getCommandData()).thenReturn(null);

        // Act
        moveCommand.execute();

        // Assert
        // Check logs or use a logger to verify that warning message was logged
    }

    @Test
    void testExecute_invalidJson_logsError() throws Exception {
        // Arrange
        CommandContext commandContext = mock(CommandContext.class);
        CommandContextHolder.setContext(commandContext);
        when(commandContext.getCommandData()).thenReturn("invalid json");

        // Act
        moveCommand.execute();

        // Assert
        // Check logs or use a logger to verify that error message was logged
    }

    @Test
    void testExecute_emptyJson_logsWarning() {
        // Arrange
        CommandContext commandContext = mock(CommandContext.class);
        CommandContextHolder.setContext(commandContext);
        when(commandContext.getCommandData()).thenReturn("");

        // Act
        moveCommand.execute();

        // Assert
        // Check logs or use a logger to verify that warning message was logged
    }

    @Test
    void testExecute_nullMoveDto_logsWarning() throws Exception {
        // Arrange
        CommandContext commandContext = mock(CommandContext.class);
        CommandContextHolder.setContext(commandContext);
        String json = objectMapper.writeValueAsString((MoveDto) null);
        when(commandContext.getCommandData()).thenReturn(json);

        // Act
        moveCommand.execute();

        // Assert
        // Check logs or use a logger to verify that warning message was logged
    }
}
