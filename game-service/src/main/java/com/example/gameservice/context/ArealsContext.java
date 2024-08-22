package com.example.gameservice.context;

import com.example.gameservice.service.GameObject;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
public class ArealsContext {
    private Map<String, List<GameObject>> surroundings = new HashMap<>();

    public void addObjectToSurrounding(String surroundingId, GameObject gameObject) {
        surroundings.putIfAbsent(surroundingId, new ArrayList<>());
        surroundings.get(surroundingId).add(gameObject);
    }

    public void removeObjectFromSurrounding(String arealId, String userId) {
        List<GameObject> gameObjects = surroundings.get(arealId);
        if (gameObjects != null) {
            gameObjects.removeIf(gameObject -> gameObject.getId_user().equals(userId));

            if (gameObjects.isEmpty()) {
                surroundings.remove(arealId);
            }
        }
    }


}
