package com.example.gameservice.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testRestTemplateBean() {
        // Проверяем, что контекст загрузился и бин RestTemplate создан
        RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
        assertThat(restTemplate).isNotNull();
    }
}
