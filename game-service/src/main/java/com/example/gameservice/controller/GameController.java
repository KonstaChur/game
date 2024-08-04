package com.example.gameservice.controller;

import com.example.gameservice.service.CommandExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/game")
@Slf4j
public class GameController {

    @Autowired
    private CommandExecutorService commandExecutorService;

    @PostMapping("/execute")
    public void executeCommands(@RequestBody String json) throws IOException {
        commandExecutorService.executeCommands(json);
    }
}
