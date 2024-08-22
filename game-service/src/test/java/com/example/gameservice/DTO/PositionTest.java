package com.example.gameservice.DTO;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testPositionGettersAndSetters() {
        // Given
        Position position = new Position();
        position.setX(10.0);
        position.setY(20.0);

        // Then
        assertThat(position.getX()).isEqualTo(10.0);
        assertThat(position.getY()).isEqualTo(20.0);
    }

    @Test
    public void testPositionAllArgsConstructor() {
        // Given
        Position position = new Position(10.0, 20.0);

        // Then
        assertThat(position.getX()).isEqualTo(10.0);
        assertThat(position.getY()).isEqualTo(20.0);
    }

    @Test
    public void testPositionNoArgsConstructor() {
        // Given
        Position position = new Position();

        // Then
        assertThat(position.getX()).isNull();
        assertThat(position.getY()).isNull();
    }

    @Test
    public void testPositionNotNullValidation() {
        // Given
        Position position = new Position();

        // When
        Set<ConstraintViolation<Position>> violations = validator.validate(position);

        // Then
        assertThat(violations).hasSize(2); // Expecting 2 violations due to both fields being null

        for (ConstraintViolation<Position> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            assertThat(propertyPath).isIn("x", "y");
            assertThat(violation.getMessage()).isEqualTo("must not be null");
        }
    }

    @Test
    public void testPositionEqualsAndHashCode() {
        // Given
        Position position1 = new Position(10.0, 20.0);
        Position position2 = new Position(10.0, 20.0);
        Position position3 = new Position(15.0, 25.0);

        // Then
        assertThat(position1).isEqualTo(position2);
        assertThat(position1.hashCode()).isEqualTo(position2.hashCode());

        assertThat(position1).isNotEqualTo(position3);
        assertThat(position1.hashCode()).isNotEqualTo(position3.hashCode());
    }

    @Test
    public void testPositionToString() {
        // Given
        Position position = new Position(10.0, 20.0);

        // When
        String result = position.toString();

        // Then
        assertThat(result).contains("10.0");
        assertThat(result).contains("20.0");
    }
}
