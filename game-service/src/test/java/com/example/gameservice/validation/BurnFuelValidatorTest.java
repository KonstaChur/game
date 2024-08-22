package com.example.gameservice.validation;

import com.example.gameservice.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BurnFuelValidatorTest {

    private BurnFuelValidator validator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        validator = new BurnFuelValidator();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testValidData() throws Exception {
        String validJson = "{\"currentFuel\": 50.0, \"fuelConsumption\": 10.0}";
        JsonNode jsonNode = objectMapper.readTree(validJson);

        assertDoesNotThrow(() -> validator.validate(jsonNode));
    }

    @Test
    void testMissingCurrentFuel() throws Exception {
        String invalidJson = "{\"fuelConsumption\": 10.0}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testInvalidCurrentFuelType() throws Exception {
        String invalidJson = "{\"currentFuel\": \"string\", \"fuelConsumption\": 10.0}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testMissingFuelConsumption() throws Exception {
        String invalidJson = "{\"currentFuel\": 50.0}";
        JsonNode jsonNode = objectMapper.readTree(invalidJson);

        assertThrows(ValidationException.class, () -> validator.validate(jsonNode));
    }

    @Test
    void testInvalidFuelConsumptionType() throws Exception {
        String invalidJson = "{\"currentFuel\": 50.0, \"fuelConsumption\": \"string\"}";
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
