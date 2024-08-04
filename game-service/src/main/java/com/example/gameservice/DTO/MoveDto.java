package com.example.gameservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoveDto {

    @NotNull
    String id_user;

    @NotNull
    String id_game;

    @NotNull
    Position position;

    @NotNull
    Velocity velocity;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Position {
        @NotNull
        private double x;
        @NotNull
        private double y;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Velocity {
        @NotNull
        private double x;
        @NotNull
        private double y;
    }

}
