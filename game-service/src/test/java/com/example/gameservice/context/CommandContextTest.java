package com.example.gameservice.context;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandContextTest {

    private CommandContext commandContext;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        commandContext = new CommandContext();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testInitialState() {
        // Test initial state of CommandContext
        assertThat(commandContext.getToken()).isNull();
        assertThat(commandContext.getCommandsNode()).isNull();
        assertThat(commandContext.getRoutingKey()).isNull();
        assertThat(commandContext.getCommandData()).isNull();
        assertThat(commandContext.getThreadId()).isZero();
        assertThat(commandContext.isStopExecution()).isFalse();
    }

    @Test
    public void testSettersAndGetters() throws Exception {
        // Test setting and getting token
        commandContext.setToken("sampleToken");
        assertThat(commandContext.getToken()).isEqualTo("sampleToken");

        // Test setting and getting commandsNode
        JsonNode node = objectMapper.readTree("{\"key\":\"value\"}");
        commandContext.setCommandsNode(node);
        assertThat(commandContext.getCommandsNode()).isEqualTo(node);

        // Test setting and getting routingKey
        commandContext.setRoutingKey("sampleRoutingKey");
        assertThat(commandContext.getRoutingKey()).isEqualTo("sampleRoutingKey");

        // Test setting and getting commandData
        commandContext.setCommandData("sampleCommandData");
        assertThat(commandContext.getCommandData()).isEqualTo("sampleCommandData");

        // Test setting and getting threadId
        commandContext.setThreadId(12345L);
        assertThat(commandContext.getThreadId()).isEqualTo(12345L);

        // Test setting and getting stopExecution
        commandContext.setStopExecution(true);
        assertThat(commandContext.isStopExecution()).isTrue();
    }
}
