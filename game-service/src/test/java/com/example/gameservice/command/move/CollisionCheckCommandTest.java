package com.example.gameservice.command.move;

import com.example.gameservice.DTO.Position;
import com.example.gameservice.context.ArealsContext;
import com.example.gameservice.exception.CommandException;
import com.example.gameservice.service.CollisionCheckService;
import com.example.gameservice.service.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CollisionCheckCommandTest {

    @Mock
    private GameObject gameObject;

    @Mock
    private CollisionCheckService checkService;

    @Mock
    private ArealsContext arealsContext;

    @InjectMocks
    private CollisionCheckCommand collisionCheckCommand;

    private String expectedArealId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Устанавливаем значения для stepX и stepY напрямую
        collisionCheckCommand.stepX = 10;
        collisionCheckCommand.stepY = 20;

        // Вычисляем expectedArealId
        Position position = new Position(30.0, 40.0);
        double x = position.getX() / collisionCheckCommand.stepX;
        double y = position.getY() / collisionCheckCommand.stepY;
        expectedArealId = "cell_" + (int)x + "_" + (int)y;
    }

    @Test
    void testExecute_NoCollision() {
        when(gameObject.getPosition()).thenReturn(new Position(30.0, 40.0));
        when(gameObject.getId_user()).thenReturn("user1");
        when(gameObject.getArealId()).thenReturn(null);

        when(checkService.collisionCheck(expectedArealId)).thenReturn(false);

        collisionCheckCommand.execute();

        verify(checkService, times(1)).collisionCheck(expectedArealId);
        verify(arealsContext, times(1)).addObjectToSurrounding(expectedArealId, gameObject);
        verify(arealsContext, never()).removeObjectFromSurrounding(anyString(), anyString());
    }

    @Test
    void testExecute_WithCollision() {
        // Подготовка
        Position position = new Position(30.0, 40.0);
        when(gameObject.getPosition()).thenReturn(position);
        when(gameObject.getId_user()).thenReturn("user1");
        when(gameObject.getArealId()).thenReturn(null);

        // Настройка поведения мока для выброса CommandException через Answer
        String expectedArealId = "cell_" + (int)(30.0 / 10) + "_" + (int)(40.0 / 20); // "cell_3_2"
        doAnswer((Answer<Void>) invocation -> {
            throw new CommandException("Collision exception");
        }).when(checkService).collisionCheck(expectedArealId);

        // Выполнение и проверка
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> collisionCheckCommand.execute());
        assertEquals("Collision exception", thrown.getCause().getMessage()); // Проверка сообщения внутреннего исключения
    }

    @Test
    void testExecute_WhenExceptionThrown() {
        // Подготовка
        Position position = new Position(30.0, 40.0);
        when(gameObject.getPosition()).thenReturn(position);
        when(gameObject.getId_user()).thenReturn("user1");

        // Настройка поведения мока для выброса CommandException через Answer
        String expectedArealId = "cell_" + (int)(30.0 / 10) + "_" + (int)(40.0 / 20);
        doAnswer((Answer<Void>) invocation -> {
            throw new CommandException("Collision exception");
        }).when(checkService).collisionCheck(expectedArealId);

        // Выполнение и проверка
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> collisionCheckCommand.execute());
        assertEquals("Collision exception", thrown.getCause().getMessage()); // Проверка сообщения внутреннего исключения
    }

    @Test
    void testCheckArealId() {
        when(gameObject.getPosition()).thenReturn(new Position(30.0, 40.0));
        when(gameObject.getId_user()).thenReturn("user1");
        when(gameObject.getArealId()).thenReturn("cell_2_2");

        collisionCheckCommand.checkArealId(expectedArealId);

        verify(arealsContext, times(1)).removeObjectFromSurrounding("cell_2_2", "user1");
        verify(gameObject, times(1)).setArealId(expectedArealId);
    }

    @Test
    void testCheckArealId_NoChange() {
        // Подготовка
        Position position = new Position(30.0, 40.0);
        when(gameObject.getPosition()).thenReturn(position);
        when(gameObject.getId_user()).thenReturn("user1");
        when(gameObject.getArealId()).thenReturn("cell_3_2"); // Устанавливаем текущий arealId

        // Выполнение
        collisionCheckCommand.checkArealId("cell_3_2"); // Новый arealId совпадает с текущим

        // Проверка
        verify(arealsContext, never()).removeObjectFromSurrounding(anyString(), anyString());
        verify(gameObject, never()).setArealId(anyString()); // Убедитесь, что setArealId не вызывается
    }
}
