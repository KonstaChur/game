package com.example.gameservice.state;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExecutionStateTest {

    @Test
    @Order(1)
    void testIsStopExecutionInitiallyFalse() {
        ExecutionState state = new ExecutionState();
        assertFalse(state.isStopExecution(), "Expected stopExecution to be false initially");
    }

    @Test
    @Order(2)
    void testSetStopExecution() {
        ExecutionState state = new ExecutionState();
        state.setStopExecution(true);
        assertTrue(state.isStopExecution(), "Expected stopExecution to be true after setting it to true");
    }

    @Test
    @Order(3)
    void testClearStopExecution() {
        ExecutionState state = new ExecutionState();
        state.setStopExecution(true);
        assertTrue(state.isStopExecution(), "Expected stopExecution to be true before clearing");
        state.clear();
        assertFalse(state.isStopExecution(), "Expected stopExecution to be false after clearing");
    }
}
