package com.example.gameservice.command.move;

import com.example.gameservice.DTO.Position;
import com.example.gameservice.command.ICommand;
import com.example.gameservice.context.ArealsContext;
import com.example.gameservice.exception.CommandException;
import com.example.gameservice.service.CollisionCheckService;
import com.example.gameservice.service.GameObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("collisionCheck")
@Slf4j
@RequiredArgsConstructor
public class CollisionCheckCommand implements ICommand {

    private final GameObject gameObject;

    private final CollisionCheckService checkService;

    private final ArealsContext arealsContext;

    @Value("${areal.stepX}")
    public Integer stepX;
    @Value("${areal.stepY}")
    public Integer stepY;

    @Override
    public void execute() {

        Position position = gameObject.getPosition();
        int x = (int) (position.getX() / stepX);
        int y = (int) (position.getY() / stepY);

        String arealId = "cell_" + x + "_" + y;
        try {
            if (checkService.collisionCheck(arealId)) {
                throw new CommandException("Collision detected for object: " + gameObject.getId_user());
            } else {
                checkArealId(arealId);
                arealsContext.addObjectToSurrounding(arealId, gameObject);
                log.info("Проверка пройдена успешно");
            }
        } catch (CommandException e) {
            log.error("Error during collision check: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void checkArealId(String arealId) {
        String gameObjectArealId = gameObject.getArealId();
        if (gameObjectArealId == null || !gameObjectArealId.equals(arealId)) {
            if (gameObjectArealId != null) {
                arealsContext.removeObjectFromSurrounding(gameObjectArealId, gameObject.getId_user());
            }
            gameObject.setArealId(arealId);
        }
    }

}

