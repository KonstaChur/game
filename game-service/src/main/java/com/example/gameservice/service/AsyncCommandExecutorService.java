package com.example.gameservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@EnableAsync
@Slf4j
public class AsyncCommandExecutorService {

    private final CommandExecutorService commandExecutorService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Async
    public void executeCommandsAsync(String token, String json) {
        executorService.submit(() -> {
            try {
                commandExecutorService.executeCommands(token, json);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }
}
