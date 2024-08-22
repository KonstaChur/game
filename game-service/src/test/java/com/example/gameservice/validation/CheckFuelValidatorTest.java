package com.example.gameservice.validation;

import com.example.gameservice.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckFuelValidatorTest {

    private CheckFuelValidator validator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        validator = new CheckFuelValidator();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testValidData() throws Exception {
        String validJson = "{\"currentFuel\": 100.0, \"fuelConsumption\": 20.0}";
        JsonNode jsonNode = objectMapper.readTree(validJson);

        assertDoesNotThrow(() -> validator.validate(jsonNode));
    }

    @Test
    void testMissingCurrentFuel() throws Exception {
        String invalidJson = "{\"fuelConsumption\": 20.0}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testInvalidCurrentFuelType() throws Exception {
        String invalidJson = "{\"currentFuel\": \"string\", \"fuelConsumption\": 20.0}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testMissingFuelConsumption() throws Exception {
        String invalidJson = "{\"currentFuel\": 100.0}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testInvalidFuelConsumptionType() throws Exception {
        String invalidJson = "{\"currentFuel\": 100.0, \"fuelConsumption\": \"string\"}";
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
    void testInvalidCurrentFuelAndFuelConsumptionType() throws Exception {
        String invalidJson = "{\"currentFuel\": \"string\", \"fuelConsumption\": \"string\"}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }
}
