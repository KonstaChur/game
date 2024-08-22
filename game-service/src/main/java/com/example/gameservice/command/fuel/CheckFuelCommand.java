package com.example.gameservice.command.fuel;

import com.example.gameservice.DTO.FuelDto;
import com.example.gameservice.command.ICommand;
import com.example.gameservice.context.CommandContext;
import com.example.gameservice.exception.CommandException;
import com.example.gameservice.service.CommandContextHolder;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("checkFuel")
@Slf4j
@RequiredArgsConstructor
public class CheckFuelCommand implements ICommand {

    @Override
    public void execute() {
        CommandContext commandContext = CommandContextHolder.getContext();
        if (commandContext == null) {
            throw new IllegalStateException("Command context is not set");
        }

        try {
            String json = commandContext.getCommandData();
            Gson gson = new Gson();
            FuelDto fuelDto = gson.fromJson(json, FuelDto.class);

            double remainingFuel = fuelDto.getCurrentFuel() - fuelDto.getFuelConsumption();

            if (remainingFuel < 0) {
                String errorMessage = "Нехватка топлива";
                throw new CommandException(errorMessage);
            }
            log.info("Проверка топлива прошла успешно");
        } catch (CommandException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
