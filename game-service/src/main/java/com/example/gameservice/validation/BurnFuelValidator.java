package com.example.gameservice.validation;

import com.example.gameservice.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component("burnFuelvalidator")
public class BurnFuelValidator implements CommandValidator {
    @Override
    public void validate(JsonNode data) throws ValidationException {
        if (!data.has("currentFuel") || !data.get("currentFuel").isDouble()) {
            throw new ValidationException("Invalid or missing 'currentFuel'");
        }
        if (!data.has("fuelConsumption") || !data.get("fuelConsumption").isDouble()) {
            throw new ValidationException("Invalid or missing 'fuelConsumption'");
        }
    }
}
