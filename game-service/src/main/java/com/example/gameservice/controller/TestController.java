package com.example.gameservice.controller;

import com.example.gameservice.messaging.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private Producer producer;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        producer.sendMessage("RabbitConfig.EXCHANGE_NAME", "user123");
        return "Message sent!";
    }
}
