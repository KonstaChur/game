package com.example.gameservice.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandExceptionTest {

    @Test
    public void testCommandExceptionWithMessage() {
        // Given
        String errorMessage = "This is an error message";

        // When
        CommandException exception = new CommandException(errorMessage);

        // Then
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
        assertThat(exception.getCause()).isNull();
    }

    @Test
    public void testCommandExceptionWithMessageAndCause() {
        // Given
        String errorMessage = "This is an error message";
        Throwable cause = new RuntimeException("This is the cause");

        // When
        CommandException exception = new CommandException(errorMessage, cause);

        // Then
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    public void testCommandExceptionWithCause() {
        // Given
        Throwable cause = new RuntimeException("This is the cause");

        // When
        CommandException exception = new CommandException(cause);

        // Then
        assertThat(exception.getMessage()).isEqualTo(cause.toString());
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
