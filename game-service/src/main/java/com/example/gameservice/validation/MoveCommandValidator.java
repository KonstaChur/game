package com.example.gameservice.validation;

import com.example.gameservice.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component("movecommandvalidator")
public class MoveCommandValidator implements CommandValidator {
    @Override
    public void validate(JsonNode data) throws ValidationException {
        if (!data.has("position")) {
            throw new ValidationException("Missing 'position'");
        }
        if (!data.get("position").has("x") || !data.get("position").get("x").isDouble()) {
            throw new ValidationException("Invalid or missing 'position.x'");
        }
        if (!data.get("position").has("y") || !data.get("position").get("y").isDouble()) {
            throw new ValidationException("Invalid or missing 'position.y'");
        }
        if (!data.has("velocity")) {
            throw new ValidationException("Missing 'velocity'");
        }
        if (!data.get("velocity").has("x") || !data.get("velocity").get("x").isDouble()) {
            throw new ValidationException("Invalid or missing 'velocity.x'");
        }
        if (!data.get("velocity").has("y") || !data.get("velocity").get("y").isDouble()) {
            throw new ValidationException("Invalid or missing 'velocity.y'");
        }
    }
}
