package com.example.authservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertNull(user.getName());
        assertNull(user.getPassword());
        assertFalse(user.isPlayer());
    }

    @Test
    void testParameterizedConstructor() {
        User user = new User()
                .setName("Alice")
                .setPassword("secret")
                .setPlayer(true);

        assertEquals("Alice", user.getName());
        assertEquals("secret", user.getPassword());
        assertTrue(user.isPlayer());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setName("Bob");
        user.setPassword("password");
        user.setPlayer(true);

        assertEquals("Bob", user.getName());
        assertEquals("password", user.getPassword());
        assertTrue(user.isPlayer());
    }

    @Test
    void testToString() {
        User user = new User()
                .setName("Charlie")
                .setPassword("pass")
                .setPlayer(false);

        String expected = "User(name=Charlie, password=pass, player=false)";
        assertEquals(expected, user.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User()
                .setName("David")
                .setPassword("1234")
                .setPlayer(true);

        User user2 = new User()
                .setName("David")
                .setPassword("1234")
                .setPlayer(true);

        User user3 = new User()
                .setName("Eve")
                .setPassword("5678")
                .setPlayer(false);

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
}
