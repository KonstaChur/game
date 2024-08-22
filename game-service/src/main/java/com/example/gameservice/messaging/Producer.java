package com.example.gameservice.messaging;



import com.example.gameservice.config.RabbitConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(Object message, String key) {
        try {

            String jsonMessage = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, key, jsonMessage);

        } catch (JsonProcessingException e) {
            log.error("Error converting message to JSON", e);
        } catch (AmqpException e) {
            log.error("Error sending message to RabbitMQ", e);
        }
    }
}

