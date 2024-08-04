package com.example.gameservice.validation;

import com.example.gameservice.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonNode;

public interface CommandValidator {
    void validate(JsonNode data) throws ValidationException;
}
