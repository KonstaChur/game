package com.example.gameservice.context;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CommandContext {
    private String token;
    private JsonNode commandsNode;
    private String routingKey;
    private String commandData;
    private long threadId;
    private boolean stopExecution;

    public void setStopExecution(boolean stopExecution) {
        this.stopExecution = stopExecution;
    }

    public boolean isStopExecution() {
        return stopExecution;
    }
}
