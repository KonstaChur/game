package com.example.gameservice.DTO;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class VelocityTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testVelocityGettersAndSetters() {
        // Given
        Velocity velocity = new Velocity();
        velocity.setX(5.0);
        velocity.setY(10.0);

        // Then
        assertThat(velocity.getX()).isEqualTo(5.0);
        assertThat(velocity.getY()).isEqualTo(10.0);
    }

    @Test
    public void testVelocityAllArgsConstructor() {
        // Given
        Velocity velocity = new Velocity(5.0, 10.0);

        // Then
        assertThat(velocity.getX()).isEqualTo(5.0);
        assertThat(velocity.getY()).isEqualTo(10.0);
    }

    @Test
    public void testVelocityNoArgsConstructor() {
        // Given
        Velocity velocity = new Velocity();

        // Then
        assertThat(velocity.getX()).isNull();
        assertThat(velocity.getY()).isNull();
    }

    @Test
    public void testVelocityNotNullValidation() {
        // Given
        Velocity velocity = new Velocity();

        // When
        Set<ConstraintViolation<Velocity>> violations = validator.validate(velocity);

        // Then
        assertThat(violations).hasSize(2); // Expecting 2 violations due to both fields being null

        for (ConstraintViolation<Velocity> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            assertThat(propertyPath).isIn("x", "y");
            assertThat(violation.getMessage()).isEqualTo("must not be null");
        }
    }

    @Test
    public void testVelocityEqualsAndHashCode() {
        // Given
        Velocity velocity1 = new Velocity(5.0, 10.0);
        Velocity velocity2 = new Velocity(5.0, 10.0);
        Velocity velocity3 = new Velocity(15.0, 20.0);

        // Then
        assertThat(velocity1).isEqualTo(velocity2);
        assertThat(velocity1.hashCode()).isEqualTo(velocity2.hashCode());

        assertThat(velocity1).isNotEqualTo(velocity3);
        assertThat(velocity1.hashCode()).isNotEqualTo(velocity3.hashCode());
    }

    @Test
    public void testVelocityToString() {
        // Given
        Velocity velocity = new Velocity(5.0, 10.0);

        // When
        String result = velocity.toString();

        // Then
        assertThat(result).contains("5.0");
        assertThat(result).contains("10.0");
    }
}
