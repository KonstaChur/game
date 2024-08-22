package com.example.gameservice.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

@SpringBootTest
public class RabbitConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testGameExchange() {
        DirectExchange directExchange = context.getBean(DirectExchange.class);
        assertThat(directExchange).isNotNull();
        assertThat(directExchange.getName()).isEqualTo(RabbitConfig.EXCHANGE_NAME);
    }

    @Test
    public void testGameQueue() {
        Queue queue = context.getBean(Queue.class);
        assertThat(queue).isNotNull();
        assertThat(queue.getName()).isEqualTo(RabbitConfig.QUEUE_NAME);
        assertThat(queue.isDurable()).isTrue();
    }

    @Test
    public void testBinding() {
        Binding binding = context.getBean(Binding.class);
        assertThat(binding).isNotNull();
        assertThat(binding.getExchange()).isEqualTo(RabbitConfig.EXCHANGE_NAME);
        assertThat(binding.getDestination()).isEqualTo(RabbitConfig.QUEUE_NAME);
        assertThat(binding.getRoutingKey()).isEqualTo("routing-key");
    }
}
