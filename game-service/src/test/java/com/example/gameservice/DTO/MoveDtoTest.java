package com.example.gameservice.DTO;

import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MoveDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testMoveDtoGettersAndSetters() {
        // Given
        Position position = new Position(1.0, 2.0);
        Velocity velocity = new Velocity(0.5, 1.5);

        MoveDto moveDto = new MoveDto();
        moveDto.setId_user("user123");
        moveDto.setId_game("game456");
        moveDto.setPosition(position);
        moveDto.setVelocity(velocity);

        // Then
        assertThat(moveDto.getId_user()).isEqualTo("user123");
        assertThat(moveDto.getId_game()).isEqualTo("game456");
        assertThat(moveDto.getPosition()).isEqualTo(position);
        assertThat(moveDto.getVelocity()).isEqualTo(velocity);
    }

    @Test
    public void testMoveDtoNotNullValidation() {
        // Given
        MoveDto moveDto = new MoveDto();

        // When
        Set<ConstraintViolation<MoveDto>> violations = validator.validate(moveDto);

        // Then
        assertThat(violations).hasSize(4); // Expecting 4 violations due to all fields being null

        for (ConstraintViolation<MoveDto> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            assertThat(propertyPath).isIn("id_user", "id_game", "position", "velocity");
            assertThat(violation.getMessage()).isEqualTo("must not be null");
        }
    }

    @Test
    public void testMoveDtoEqualsAndHashCode() {
        // Given
        Position position = new Position(1.0, 2.0);
        Velocity velocity = new Velocity(0.5, 1.5);

        MoveDto moveDto1 = new MoveDto();
        moveDto1.setId_user("user123");
        moveDto1.setId_game("game456");
        moveDto1.setPosition(position);
        moveDto1.setVelocity(velocity);

        MoveDto moveDto2 = new MoveDto();
        moveDto2.setId_user("user123");
        moveDto2.setId_game("game456");
        moveDto2.setPosition(position);
        moveDto2.setVelocity(velocity);

        // Then
        assertThat(moveDto1).isEqualTo(moveDto2);
        assertThat(moveDto1.hashCode()).isEqualTo(moveDto2.hashCode());

        moveDto2.setVelocity(new Velocity(1.0, 2.0));
        assertThat(moveDto1).isNotEqualTo(moveDto2);
        assertThat(moveDto1.hashCode()).isNotEqualTo(moveDto2.hashCode());
    }

    @Test
    public void testMoveDtoToString() {
        // Given
        Position position = new Position(1.0, 2.0);
        Velocity velocity = new Velocity(0.5, 1.5);

        MoveDto moveDto = new MoveDto();
        moveDto.setId_user("user123");
        moveDto.setId_game("game456");
        moveDto.setPosition(position);
        moveDto.setVelocity(velocity);

        // When
        String result = moveDto.toString();

        // Then
        assertThat(result).contains("user123");
        assertThat(result).contains("game456");
        assertThat(result).contains("Position");
        assertThat(result).contains("Velocity");
    }
}
