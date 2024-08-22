package com.example.gameservice.command.fuel;

import com.example.gameservice.DTO.FuelDto;
import com.example.gameservice.command.ICommand;
import com.example.gameservice.context.CommandContext;
import com.example.gameservice.service.CommandContextHolder;
import com.example.gameservice.service.GameObject;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("burnFuel")
@Slf4j
@RequiredArgsConstructor
public class BurnFuelCommand implements ICommand {

    private final GameObject gameObject;

    @Override
    public void execute() {
        CommandContext commandContext = CommandContextHolder.getContext();
        if (commandContext == null) {
            log.error("Command context is not available.");
            return;
        }

        String json = commandContext.getCommandData();
        Gson gson = new Gson();

        FuelDto fuelDto = null;
        try {
            fuelDto = gson.fromJson(json, FuelDto.class);
        } catch (Exception e) {
            log.error("Failed to parse command data to FuelDto", e);
        }

        if (fuelDto != null && fuelDto.getCurrentFuel() != null && fuelDto.getFuelConsumption() != null) {
            double remainingFuel = fuelDto.getCurrentFuel() - fuelDto.getFuelConsumption();
            gameObject.setCurrentFuel(String.valueOf(remainingFuel));
            log.info("Fuel updated successfully. Remaining fuel: {}", remainingFuel);
        } else {
            log.warn("FuelDto is null or contains null values after parsing, no action will be taken.");
        }
    }
}
