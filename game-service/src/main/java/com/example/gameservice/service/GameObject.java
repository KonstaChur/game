package com.example.gameservice.service;

import com.example.gameservice.DTO.FuelDto;
import com.example.gameservice.DTO.Position;
import com.example.gameservice.DTO.Velocity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class GameObject {
    private String Id_user;
    private String Id_game;
    private Position position;
    private String arealId;
    private String currentFuel;
    private Velocity velocity;
    private FuelDto fuelDto;
}
