package com.example.userservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "game-exchange";
    public static final String QUEUE_NAME = "game-queue";

    @Bean
    public DirectExchange gameExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue gameQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding binding(Queue gameQueue, DirectExchange gameExchange) {
        return BindingBuilder.bind(gameQueue).to(gameExchange).with("routing-key");
    }
}
