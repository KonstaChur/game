package com.example.gameservice.command.message;

import com.example.gameservice.command.ICommand;
import com.example.gameservice.messaging.Producer;
import com.example.gameservice.service.GameObject;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("message")
@Slf4j
@RequiredArgsConstructor
public class MessageCommand implements ICommand {

    private final GameObject gameObject;
    private final Producer producer;


    @Override
    public void execute() {
        Gson gson = new Gson();
        String json = gson.toJson(gameObject);
        String routingKey = gameObject.getId_user();
        producer.sendMessage(json, routingKey);
        log.info("Отправлен gameObject в RabbitMQ с ключом маршрутизации: {}", routingKey);
    }
}
