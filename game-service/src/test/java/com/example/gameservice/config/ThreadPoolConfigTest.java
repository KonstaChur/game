package com.example.gameservice.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThreadPoolConfigTest {

    @Autowired
    private ExecutorService taskExecutor;

    @Test
    public void testTaskExecutor() {

        assertThat(taskExecutor).isNotNull();

        assertThat(taskExecutor).isInstanceOf(ThreadPoolExecutor.class);

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) taskExecutor;

        assertThat(threadPoolExecutor.getCorePoolSize()).isEqualTo(5);
    }
}
