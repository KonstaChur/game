package com.example.gameservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class AsyncCommandExecutorServiceTest {

    @Mock
    private CommandExecutorService commandExecutorService;

    @Mock
    private ExecutorService mockExecutorService;

    @InjectMocks
    private AsyncCommandExecutorService asyncCommandExecutorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Replace real ExecutorService with mock
        ReflectionTestUtils.setField(asyncCommandExecutorService, "executorService", mockExecutorService);
    }

    @Test
    void executeCommandsAsync_successful() throws IOException {
        // Arrange
        String token = "token";
        String json = "{\"key\":\"value\"}";

        // Act
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> asyncCommandExecutorService.executeCommandsAsync(token, json));

        // Wait for the async execution to complete
        future.join();

        // Assert
        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        verify(mockExecutorService).submit(runnableCaptor.capture());

        // Ensure the Runnable task is captured
        Runnable capturedRunnable = runnableCaptor.getValue();
        assertDoesNotThrow(() -> capturedRunnable.run());  // Run the captured task

        // Verify that executeCommands was called
        verify(commandExecutorService).executeCommands(token, json);
    }

    @Test
    void executeCommandsAsync_exception() throws IOException {
        // Arrange
        String token = "token";
        String json = "{\"key\":\"value\"}";

        doThrow(new RuntimeException("Test exception")).when(commandExecutorService).executeCommands(anyString(), anyString());

        // Act
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> asyncCommandExecutorService.executeCommandsAsync(token, json));

        // Wait for the async execution to complete
        future.join();

        // Assert
        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        verify(mockExecutorService).submit(runnableCaptor.capture());

        // Ensure the Runnable task is captured
        Runnable capturedRunnable = runnableCaptor.getValue();
        assertDoesNotThrow(() -> capturedRunnable.run());  // Run the captured task

        // Verify that executeCommands was called
        verify(commandExecutorService).executeCommands(token, json);
    }
}
