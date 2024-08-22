package com.example.gameservice.validation;

import com.example.gameservice.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveValidatorTest {

    private MoveValidator validator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        validator = new MoveValidator();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testValidData() throws Exception {
        String validJson = "{\"position\": {\"x\": 10.0, \"y\": 20.0}, \"velocity\": {\"x\": 5.0, \"y\": 10.0}}";
        JsonNode jsonNode = objectMapper.readTree(validJson);

        assertDoesNotThrow(() -> validator.validate(jsonNode));
    }

    @Test
    void testMissingPosition() throws Exception {
        String invalidJson = "{\"velocity\": {\"x\": 5.0, \"y\": 10.0}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testMissingPositionX() throws Exception {
        String invalidJson = "{\"position\": {\"y\": 20.0}, \"velocity\": {\"x\": 5.0, \"y\": 10.0}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testInvalidPositionXType() throws Exception {
        String invalidJson = "{\"position\": {\"x\": \"string\", \"y\": 20.0}, \"velocity\": {\"x\": 5.0, \"y\": 10.0}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testMissingPositionY() throws Exception {
        String invalidJson = "{\"position\": {\"x\": 10.0}, \"velocity\": {\"x\": 5.0, \"y\": 10.0}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testInvalidPositionYType() throws Exception {
        String invalidJson = "{\"position\": {\"x\": 10.0, \"y\": \"string\"}, \"velocity\": {\"x\": 5.0, \"y\": 10.0}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testMissingVelocity() throws Exception {
        String invalidJson = "{\"position\": {\"x\": 10.0, \"y\": 20.0}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testMissingVelocityX() throws Exception {
        String invalidJson = "{\"position\": {\"x\": 10.0, \"y\": 20.0}, \"velocity\": {\"y\": 10.0}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testInvalidVelocityXType() throws Exception {
        String invalidJson = "{\"position\": {\"x\": 10.0, \"y\": 20.0}, \"velocity\": {\"x\": \"string\", \"y\": 10.0}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testMissingVelocityY() throws Exception {
        String invalidJson = "{\"position\": {\"x\": 10.0, \"y\": 20.0}, \"velocity\": {\"x\": 5.0}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testInvalidVelocityYType() throws Exception {
        String invalidJson = "{\"position\": {\"x\": 10.0, \"y\": 20.0}, \"velocity\": {\"x\": 5.0, \"y\": \"string\"}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testMissingBothFields() throws Exception {
        String invalidJson = "{}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testInvalidPositionAndVelocity() throws Exception {
        String invalidJson = "{\"position\": {\"x\": \"string\", \"y\": \"string\"}, \"velocity\": {\"x\": \"string\", \"y\": \"string\"}}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }
}
