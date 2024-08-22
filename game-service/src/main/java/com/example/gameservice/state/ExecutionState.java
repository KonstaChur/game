package com.example.gameservice.state;

import org.springframework.stereotype.Component;

@Component
public class ExecutionState {

    private static final ThreadLocal<Boolean> stopExecution = ThreadLocal.withInitial(() -> false);

    public boolean isStopExecution() {
        return stopExecution.get();
    }

    public void setStopExecution(boolean stop) {
        stopExecution.set(stop);
    }

    public void clear() {
        stopExecution.remove();
    }
}
