package com.example.gameservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class FuelDto {

    @NotNull
    String id_user;

    @NotNull
    String id_game;

    @NotNull
    Double currentFuel;

    @NotNull
    Double fuelConsumption;
}
