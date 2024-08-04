package com.example.gameservice.command.fuel;

import com.example.gameservice.command.ICommand;
import com.example.gameservice.context.CommandContext;
import com.example.gameservice.exception.CommandException;
import com.example.gameservice.messaging.Producer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("checkfuelcommand")
@Slf4j
public class CheckFuelCommand implements ICommand {

    @Autowired
    private CommandContext commandContext;
    @Autowired
    private Producer producer;

    @Override
    public void execute() {
        try {
            String json = commandContext.getCommandData();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            String idUser = jsonObject.getAsJsonObject("data").get("id_user").getAsString();


            JsonObject data = jsonObject.getAsJsonObject("data");
            double currentFuel = data.get("currentFuel").getAsDouble();
            double fuelConsumption = data.get("fuelConsumption").getAsDouble();

            double remainingFuel = currentFuel - fuelConsumption;
            log.info("Remaining fuel: {}", remainingFuel);

            if (remainingFuel < 0) {
                String errorMessage = "Fuel has run out";
                producer.sendMessage(errorMessage, idUser);
                throw new CommandException(errorMessage);
            }
        } catch (CommandException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
