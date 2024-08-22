package com.example.gameservice.command.move;

import com.example.gameservice.DTO.MoveDto;
import com.example.gameservice.DTO.Position;
import com.example.gameservice.DTO.Velocity;
import com.example.gameservice.command.ICommand;
import com.example.gameservice.context.CommandContext;
import com.example.gameservice.service.CommandContextHolder;
import com.example.gameservice.service.GameObject;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("move")
@Slf4j
@RequiredArgsConstructor
public class MoveCommand implements ICommand {

    private final GameObject gameObject;

    @Override
    public void execute() {
        CommandContext commandContext = CommandContextHolder.getContext();
        if (commandContext == null) {
            log.error("Command context is not available.");
            return;
        }

        String json = commandContext.getCommandData();
        if (json == null || json.trim().isEmpty()) {
            log.warn("No command data provided.");
            return;
        }

        Gson gson = new Gson();
        MoveDto moveDto;
        try {
            moveDto = gson.fromJson(json, MoveDto.class);
        } catch (Exception e) {
            log.error("Error parsing command data: {}", e.getMessage());
            return;
        }

        if (moveDto == null) {
            log.warn("Command data could not be parsed into MoveDto.");
            return;
        }

        double newX = moveDto.getPosition().getX() + moveDto.getVelocity().getX();
        double newY = moveDto.getPosition().getY() + moveDto.getVelocity().getY();
        moveDto.getPosition().setX(newX);
        moveDto.getPosition().setY(newY);

        gameObject.setId_game(moveDto.getId_game());
        gameObject.setId_user(moveDto.getId_user());
        gameObject.setPosition(new Position(newX, newY));

        log.info("Updated position: {}", moveDto);
    }
}
