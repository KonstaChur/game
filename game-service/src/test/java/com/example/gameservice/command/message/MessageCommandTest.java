package com.example.gameservice.command.message;

import com.example.gameservice.service.GameObject;
import com.example.gameservice.messaging.Producer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class MessageCommandTest {

    @Mock
    private GameObject gameObject;

    @Mock
    private Producer producer;

    @InjectMocks
    private MessageCommand messageCommand;

    private Gson gson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gson = new Gson();
    }

    @Test
    void testExecute_withValidData() {
        // Given
        String userId = "user123";
        when(gameObject.getId_user()).thenReturn(userId);
        when(gameObject.toString()).thenReturn("gameObjectString"); // This should be replaced with actual JSON if needed

        // Convert gameObject to JSON
        String expectedJson = gson.toJson(gameObject);

        // When
        messageCommand.execute();

        // Then
        verify(gameObject).getId_user();
        verify(producer).sendMessage(expectedJson, userId);

        assertTrue(true);
    }

    @Test
    void testExecute_withNullUserId() {
        // Given
        when(gameObject.getId_user()).thenReturn(null);
        when(gameObject.toString()).thenReturn("gameObjectString");

        // Convert gameObject to JSON
        String expectedJson = gson.toJson(gameObject);

        // When
        messageCommand.execute();

        // Then
        verify(gameObject).getId_user();
        verify(producer).sendMessage(expectedJson, null);

        assertTrue(true);
    }
}
