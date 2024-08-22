package com.example.gameservice.DTO;

import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class FuelDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testFuelDtoGettersAndSetters() {
        // Given
        FuelDto fuelDto = new FuelDto();
        fuelDto.setId_user("user123");
        fuelDto.setId_game("game456");
        fuelDto.setCurrentFuel(100.5);
        fuelDto.setFuelConsumption(10.0);

        // Then
        assertThat(fuelDto.getId_user()).isEqualTo("user123");
        assertThat(fuelDto.getId_game()).isEqualTo("game456");
        assertThat(fuelDto.getCurrentFuel()).isEqualTo(100.5);
        assertThat(fuelDto.getFuelConsumption()).isEqualTo(10.0);
    }

    @Test
    public void testFuelDtoNotNullValidation() {
        // Given
        FuelDto fuelDto = new FuelDto();

        // When
        Set<ConstraintViolation<FuelDto>> violations = validator.validate(fuelDto);

        // Then
        assertThat(violations).hasSize(4);

        for (ConstraintViolation<FuelDto> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            assertThat(propertyPath).isIn("id_user", "id_game", "currentFuel", "fuelConsumption");
            assertThat(violation.getMessage()).isEqualTo("must not be null");
        }
    }

    @Test
    public void testFuelDtoEqualsAndHashCode() {
        // Given
        FuelDto fuelDto1 = new FuelDto();
        fuelDto1.setId_user("user123");
        fuelDto1.setId_game("game456");
        fuelDto1.setCurrentFuel(100.5);
        fuelDto1.setFuelConsumption(10.0);

        FuelDto fuelDto2 = new FuelDto();
        fuelDto2.setId_user("user123");
        fuelDto2.setId_game("game456");
        fuelDto2.setCurrentFuel(100.5);
        fuelDto2.setFuelConsumption(10.0);

        // Then
        assertThat(fuelDto1).isEqualTo(fuelDto2);
        assertThat(fuelDto1.hashCode()).isEqualTo(fuelDto2.hashCode());

        fuelDto2.setFuelConsumption(15.0);
        assertThat(fuelDto1).isNotEqualTo(fuelDto2);
        assertThat(fuelDto1.hashCode()).isNotEqualTo(fuelDto2.hashCode());
    }

    @Test
    public void testFuelDtoToString() {
        // Given
        FuelDto fuelDto = new FuelDto();
        fuelDto.setId_user("user123");
        fuelDto.setId_game("game456");
        fuelDto.setCurrentFuel(100.5);
        fuelDto.setFuelConsumption(10.0);

        // When
        String result = fuelDto.toString();

        // Then
        assertThat(result).contains("user123");
        assertThat(result).contains("game456");
        assertThat(result).contains("100.5");
        assertThat(result).contains("10.0");
    }
}
