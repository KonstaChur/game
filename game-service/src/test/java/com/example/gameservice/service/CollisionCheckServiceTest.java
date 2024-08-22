package com.example.gameservice.service;

import com.example.gameservice.DTO.Position;
import com.example.gameservice.context.ArealsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CollisionCheckServiceTest {

    @Mock
    private GameObject gameObject;

    @Mock
    private ArealsContext arealsContext;

    @InjectMocks
    private CollisionCheckService collisionCheckService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Set the radiusObject directly using ReflectionTestUtils
        ReflectionTestUtils.setField(collisionCheckService, "radiusObject", 10);
    }

    @Test
    void collisionCheck_noCollision() {
        // Arrange
        Position position = new Position(0.0, 0.0);
        Position otherPosition = new Position(20.0, 20.0); // Far enough to not collide
        String arealId = "areal1";

        when(gameObject.getPosition()).thenReturn(position);
        when(gameObject.getId_user()).thenReturn("user1");

        GameObject otherObject = mock(GameObject.class);
        when(otherObject.getPosition()).thenReturn(otherPosition);
        when(otherObject.getId_user()).thenReturn("user2");

        Map<String, List<GameObject>> surroundings = new HashMap<>();
        surroundings.put(arealId, Collections.singletonList(otherObject));
        when(arealsContext.getSurroundings()).thenReturn(surroundings);

        // Act
        boolean result = collisionCheckService.collisionCheck(arealId);

        // Assert
        assertFalse(result, "Expected no collision but found collision.");
    }

    @Test
    void collisionCheck_collision() {
        // Arrange
        Position position = new Position(0.0, 0.0);
        Position otherPosition = new Position(5.0, 5.0); // Close enough to collide
        String arealId = "areal1";

        when(gameObject.getPosition()).thenReturn(position);
        when(gameObject.getId_user()).thenReturn("user1");

        GameObject otherObject = mock(GameObject.class);
        when(otherObject.getPosition()).thenReturn(otherPosition);
        when(otherObject.getId_user()).thenReturn("user2");

        Map<String, List<GameObject>> surroundings = new HashMap<>();
        surroundings.put(arealId, Collections.singletonList(otherObject));
        when(arealsContext.getSurroundings()).thenReturn(surroundings);

        // Act
        boolean result = collisionCheckService.collisionCheck(arealId);

        // Assert
        assertTrue(result, "Expected collision but no collision found.");
    }

    @Test
    void collisionCheck_noSurroundings() {
        // Arrange
        Position position = new Position(0.0, 0.0);
        String arealId = "areal1";

        when(gameObject.getPosition()).thenReturn(position);
        when(gameObject.getId_user()).thenReturn("user1");

        when(arealsContext.getSurroundings()).thenReturn(new HashMap<>()); // Empty surroundings

        // Act
        boolean result = collisionCheckService.collisionCheck(arealId);

        // Assert
        assertFalse(result, "Expected no collision when no surroundings are present.");
    }

    @Test
    void collisionCheck_nullSurroundings() {
        // Arrange
        Position position = new Position(0.0, 0.0);
        String arealId = "areal1";

        when(gameObject.getPosition()).thenReturn(position);
        when(gameObject.getId_user()).thenReturn("user1");

        when(arealsContext.getSurroundings()).thenReturn(null); // Null surroundings

        // Act
        boolean result = collisionCheckService.collisionCheck(arealId);

        // Assert
        assertFalse(result, "Expected no collision when surroundings are null.");
    }
}
