package com.example.gameservice.context;

import com.example.gameservice.service.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

public class ArealsContextTest {

    private ArealsContext arealsContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        arealsContext = new ArealsContext();
    }

    @Test
    public void testAddObjectToSurrounding() {
        // Создаем объект GameObject
        GameObject gameObject = new GameObject();
        gameObject.setId_user("user1");

        // Добавляем объект в окружение
        arealsContext.addObjectToSurrounding("surrounding1", gameObject);

        // Проверяем, что окружение содержит объект
        Map<String, List<GameObject>> surroundings = arealsContext.getSurroundings();
        assertThat(surroundings).containsKey("surrounding1");
        assertThat(surroundings.get("surrounding1")).contains(gameObject);
    }

    @Test
    public void testRemoveObjectFromSurrounding() {
        // Создаем два объекта GameObject
        GameObject gameObject1 = new GameObject();
        gameObject1.setId_user("user1");

        GameObject gameObject2 = new GameObject();
        gameObject2.setId_user("user2");

        // Добавляем оба объекта в окружение
        arealsContext.addObjectToSurrounding("surrounding1", gameObject1);
        arealsContext.addObjectToSurrounding("surrounding1", gameObject2);

        // Удаляем один из объектов
        arealsContext.removeObjectFromSurrounding("surrounding1", "user1");

        // Проверяем, что объект был удален
        Map<String, List<GameObject>> surroundings = arealsContext.getSurroundings();
        assertThat(surroundings).containsKey("surrounding1");
        assertThat(surroundings.get("surrounding1")).doesNotContain(gameObject1);
        assertThat(surroundings.get("surrounding1")).contains(gameObject2);
    }

    @Test
    public void testRemoveObjectFromSurrounding_whenListIsEmpty() {
        // Создаем объект GameObject
        GameObject gameObject = new GameObject();
        gameObject.setId_user("user1");

        // Добавляем объект в окружение
        arealsContext.addObjectToSurrounding("surrounding1", gameObject);

        // Удаляем объект
        arealsContext.removeObjectFromSurrounding("surrounding1", "user1");

        // Проверяем, что окружение пустое
        Map<String, List<GameObject>> surroundings = arealsContext.getSurroundings();
        assertThat(surroundings).doesNotContainKey("surrounding1");
    }

    @Test
    public void testAddObjectToSurrounding_whenSurroundingAlreadyExists() {
        // Создаем объекты GameObject
        GameObject gameObject1 = new GameObject();
        gameObject1.setId_user("user1");

        GameObject gameObject2 = new GameObject();
        gameObject2.setId_user("user2");

        // Добавляем первый объект в окружение
        arealsContext.addObjectToSurrounding("surrounding1", gameObject1);

        // Добавляем второй объект в то же окружение
        arealsContext.addObjectToSurrounding("surrounding1", gameObject2);

        // Проверяем, что оба объекта присутствуют в окружении
        Map<String, List<GameObject>> surroundings = arealsContext.getSurroundings();
        assertThat(surroundings).containsKey("surrounding1");
        assertThat(surroundings.get("surrounding1")).containsExactlyInAnyOrder(gameObject1, gameObject2);
    }
}
