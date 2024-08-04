package com.example.gameservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
