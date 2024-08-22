package com.example.gameservice.service;

import com.example.gameservice.DTO.FuelDto;
import com.example.gameservice.DTO.Position;
import com.example.gameservice.DTO.Velocity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameObjectTest {

    @Test
    void testDefaultConstructor() {
        GameObject gameObject = new GameObject();
        assertNotNull(gameObject);
    }

    @Test
    void testParameterizedConstructor() {
        Position position = new Position(1.0, 1.0);
        Velocity velocity = new Velocity(0.0, 0.0);
        FuelDto fuelDto = new FuelDto("user1", "game1", 100.0, 10.0);
        GameObject gameObject = new GameObject("user1", "game1", position, "area1", "fuel1", velocity, fuelDto);

        assertEquals("user1", gameObject.getId_user());
        assertEquals("game1", gameObject.getId_game());
        assertEquals(position, gameObject.getPosition());
        assertEquals("area1", gameObject.getArealId());
        assertEquals("fuel1", gameObject.getCurrentFuel());
        assertEquals(velocity, gameObject.getVelocity());
        assertEquals(fuelDto, gameObject.getFuelDto());
    }

    @Test
    void testSettersAndGetters() {
        GameObject gameObject = new GameObject();

        Position position = new Position(2.0, 2.0);
        Velocity velocity = new Velocity(1.0, 1.0);
        FuelDto fuelDto = new FuelDto("user2", "game2", 200.0, 20.0);

        gameObject.setId_user("user2");
        gameObject.setId_game("game2");
        gameObject.setPosition(position);
        gameObject.setArealId("area2");
        gameObject.setCurrentFuel("fuel2");
        gameObject.setVelocity(velocity);
        gameObject.setFuelDto(fuelDto);

        assertEquals("user2", gameObject.getId_user());
        assertEquals("game2", gameObject.getId_game());
        assertEquals(position, gameObject.getPosition());
        assertEquals("area2", gameObject.getArealId());
        assertEquals("fuel2", gameObject.getCurrentFuel());
        assertEquals(velocity, gameObject.getVelocity());
        assertEquals(fuelDto, gameObject.getFuelDto());
    }

    @Test
    void testToString() {
        Position position = new Position(1.0, 1.0);
        Velocity velocity = new Velocity(0.0, 0.0);
        FuelDto fuelDto = new FuelDto("user1", "game1", 100.0, 10.0);
        GameObject gameObject = new GameObject("user1", "game1", position, "area1", "fuel1", velocity, fuelDto);

        String expectedString = "GameObject(Id_user=user1, Id_game=game1, position=" + position +
                ", arealId=area1, currentFuel=fuel1, velocity=" + velocity +
                ", fuelDto=" + fuelDto + ")";
        assertEquals(expectedString, gameObject.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Position position1 = new Position(1.0, 1.0);
        Velocity velocity1 = new Velocity(0.0, 0.0);
        FuelDto fuelDto1 = new FuelDto("user1", "game1", 100.0, 10.0);
        GameObject gameObject1 = new GameObject("user1", "game1", position1, "area1", "fuel1", velocity1, fuelDto1);

        Position position2 = new Position(1.0, 1.0);
        Velocity velocity2 = new Velocity(0.0, 0.0);
        FuelDto fuelDto2 = new FuelDto("user1", "game1", 100.0, 10.0);
        GameObject gameObject2 = new GameObject("user1", "game1", position2, "area1", "fuel1", velocity2, fuelDto2);

        assertEquals(gameObject1, gameObject2);
        assertEquals(gameObject1.hashCode(), gameObject2.hashCode());
    }
}
