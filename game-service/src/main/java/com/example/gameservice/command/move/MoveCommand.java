package com.example.gameservice.command.move;

import com.example.gameservice.DTO.MoveDto;
import com.example.gameservice.command.ICommand;
import com.example.gameservice.config.RabbitConfig;
import com.example.gameservice.context.CommandContext;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("movecommand")
@Slf4j
public class MoveCommand implements ICommand {

    @Autowired
    private CommandContext commandContext;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void execute() {
        String json = commandContext.getCommandData();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        JsonObject data = jsonObject.getAsJsonObject("data");
        MoveDto moveDto = gson.fromJson(data, MoveDto.class);

        double newX = moveDto.getPosition().getX() + moveDto.getVelocity().getX();
        double newY = moveDto.getPosition().getY() + moveDto.getVelocity().getY();
        moveDto.getPosition().setX(newX);
        moveDto.getPosition().setY(newY);

        log.info("Обновленные координаты движения: {}", moveDto);

        String updatedMoveDtoJson = gson.toJson(moveDto);

        String routingKey = moveDto.getId_user();
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, routingKey, updatedMoveDtoJson);

        log.info("Отправлено обновленное движение в RabbitMQ с ключом маршрутизации: {}", routingKey);
    }
}
