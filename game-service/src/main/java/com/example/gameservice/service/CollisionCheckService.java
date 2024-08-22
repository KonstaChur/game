package com.example.gameservice.service;

import com.example.gameservice.DTO.Position;
import com.example.gameservice.context.ArealsContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CollisionCheckService {

    private final GameObject gameObject;
    private final ArealsContext arealsContext;

    @Value("${object.radius}")
    private Integer radiusObject;

    public boolean collisionCheck(String arealId) {
        Position position = gameObject.getPosition();

        Map<String, List<GameObject>> surroundings = arealsContext.getSurroundings();
        if (surroundings == null) {
            return false;
        }

        List<GameObject> gameObjects = surroundings.get(arealId);
        if (gameObjects == null) {
            return false;
        }

        for (GameObject object : gameObjects) {
            if (isCollision(position, object.getPosition())) {
                log.info("Обнаружено столкновение между {} и {}", gameObject.getId_user(), object.getId_user());
                return true;
            }
        }
        return false;
    }


    /**
     * Метод проверяет коллизии по заданному алгоритму.
     * В нашем случае обьект это окружность с радиусом radiusObject
     */
    private boolean isCollision(Position pos1, Position pos2) {
        double distance = Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2));
        return distance <= radiusObject;
    }
}
