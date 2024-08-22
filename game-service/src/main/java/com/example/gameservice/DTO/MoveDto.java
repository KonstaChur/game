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
public class MoveDto {

    @NotNull
    String id_user;

    @NotNull
    String id_game;

    @NotNull
    Position position;

    @NotNull
    Velocity velocity;
}
