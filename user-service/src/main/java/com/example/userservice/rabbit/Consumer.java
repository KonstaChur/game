package com.example.userservice.rabbit;

import com.example.userservice.config.RabbitConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@AllArgsConstructor
public class Consumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void processMessage(String message) {
        try {
            log.info(message);
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
        }
    }
}