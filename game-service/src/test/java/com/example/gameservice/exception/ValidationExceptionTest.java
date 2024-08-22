package com.example.gameservice.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationExceptionTest {

    @Test
    public void testValidationExceptionWithMessage() {
        // Given
        String errorMessage = "Validation failed";

        // When
        ValidationException exception = new ValidationException(errorMessage);

        // Then
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
        assertThat(exception.getCause()).isNull();
    }

    @Test
    public void testValidationExceptionWithMessageAndCause() {
        // Given
        String errorMessage = "Validation failed";
        Throwable cause = new RuntimeException("Root cause of validation failure");

        // When
        ValidationException exception = new ValidationException(errorMessage, cause);

        // Then
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    public void testValidationExceptionWithCause() {
        // Given
        Throwable cause = new RuntimeException("Root cause of validation failure");

        // When
        ValidationException exception = new ValidationException(cause);

        // Then
        assertThat(exception.getMessage()).isEqualTo(cause.toString());
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
