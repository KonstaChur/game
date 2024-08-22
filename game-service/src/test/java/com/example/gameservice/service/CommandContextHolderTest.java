package com.example.gameservice.service;

import com.example.gameservice.context.CommandContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandContextHolderTest {

    @AfterEach
    public void tearDown() {
        // Clear the context after each test to avoid side effects
        CommandContextHolder.clearContext();
    }

    @Test
    public void testSetAndGetContext() {
        CommandContext context = new CommandContext();
        context.setToken("testToken");

        CommandContextHolder.setContext(context);
        CommandContext retrievedContext = CommandContextHolder.getContext();

        assertThat(retrievedContext).isNotNull();
        assertThat(retrievedContext.getToken()).isEqualTo("testToken");
    }

    @Test
    public void testClearContext() {
        CommandContext context = new CommandContext();
        context.setToken("testToken");

        CommandContextHolder.setContext(context);
        CommandContextHolder.clearContext();
        CommandContext retrievedContext = CommandContextHolder.getContext();

        assertThat(retrievedContext).isNull();
    }

    @Test
    public void testMultipleThreads() throws InterruptedException {
        CommandContext context1 = new CommandContext();
        context1.setToken("token1");

        CommandContext context2 = new CommandContext();
        context2.setToken("token2");

        Thread thread1 = new Thread(() -> {
            CommandContextHolder.setContext(context1);
            CommandContext retrievedContext1 = CommandContextHolder.getContext();
            assertThat(retrievedContext1).isNotNull();
            assertThat(retrievedContext1.getToken()).isEqualTo("token1");
        });

        Thread thread2 = new Thread(() -> {
            CommandContextHolder.setContext(context2);
            CommandContext retrievedContext2 = CommandContextHolder.getContext();
            assertThat(retrievedContext2).isNotNull();
            assertThat(retrievedContext2.getToken()).isEqualTo("token2");
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Ensure that context in the main thread is unaffected
        CommandContext mainThreadContext = CommandContextHolder.getContext();
        assertThat(mainThreadContext).isNull();
    }
}
