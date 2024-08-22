package com.example.gameservice.messaging;

import com.example.gameservice.config.RabbitConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private Producer producer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessage_successful() throws JsonProcessingException {
        // Arrange
        Object message = new Object();
        String key = "testKey";
        String jsonMessage = "{\"key\":\"value\"}";

        when(objectMapper.writeValueAsString(message)).thenReturn(jsonMessage);

        // Act
        producer.sendMessage(message, key);

        // Assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(rabbitTemplate).convertAndSend(eq(RabbitConfig.EXCHANGE_NAME), eq(key), captor.capture());
        assertEquals(jsonMessage, captor.getValue());

        verify(objectMapper).writeValueAsString(message);
        verifyNoMoreInteractions(rabbitTemplate, objectMapper);
    }

    @Test
    void sendMessage_jsonProcessingException() throws JsonProcessingException {
        // Arrange
        Object message = new Object();
        String key = "testKey";

        when(objectMapper.writeValueAsString(message)).thenThrow(new JsonProcessingException("JSON error") {});

        // Act
        producer.sendMessage(message, key);

        // Assert
        verify(objectMapper).writeValueAsString(message);
        verifyNoMoreInteractions(rabbitTemplate);
    }

    @Test
    void sendMessage_amqpException() throws JsonProcessingException {
        // Arrange
        Object message = new Object();
        String key = "testKey";
        String jsonMessage = "{\"key\":\"value\"}";

        when(objectMapper.writeValueAsString(message)).thenReturn(jsonMessage);
        doThrow(new AmqpException("AMQP error")).when(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());

        // Act
        producer.sendMessage(message, key);

        // Assert
        verify(rabbitTemplate).convertAndSend(eq(RabbitConfig.EXCHANGE_NAME), eq(key), eq(jsonMessage));
        verify(objectMapper).writeValueAsString(message);
    }
}
