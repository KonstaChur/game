package com.example.gameservice.controller;

import com.example.gameservice.service.AsyncCommandExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@Slf4j
@RequiredArgsConstructor
public class GameController {

    private final AsyncCommandExecutorService commandExecutorService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeCommands(@RequestHeader(value = "Authorization", required = false) String authorizationHeader, @RequestBody String json) {
        if (authorizationHeader == null) {
            log.error("Заголовок авторизации отсутствует или неверен");
            return ResponseEntity.badRequest().body("The authorization header is missing or invalid.");
        }

        String token = authorizationHeader;

        commandExecutorService.executeCommandsAsync(token, json);

        return ResponseEntity.ok("The command was executed successfully.");
    }


}
