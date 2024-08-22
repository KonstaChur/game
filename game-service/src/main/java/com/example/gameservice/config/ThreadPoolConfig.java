package com.example.gameservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfig {

    @Value("${threads-value}")
    int threadValue;

    @Bean
    public ExecutorService taskExecutor() {
        return Executors.newFixedThreadPool(5);
    }
}
