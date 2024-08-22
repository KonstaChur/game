package com.example.userservice.controller;

import com.example.userservice.service.AuthService;
import com.example.userservice.service.Move;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    private final Move move;

    @GetMapping("/test")
    public String test (){
        return "Тест прошел успешно";
    }

    @GetMapping("/testAuth")
    public String testAuth (){
        String test = authService.test();
        return "Тест прошел успешно"+test;
    }

    @GetMapping("/authenticate")
    public String authenticate() {
        return authService.authenticate();
    }

    @GetMapping("/move")
    public String sendCommands() throws IOException {
         return move.executeCommands();
    }
}
