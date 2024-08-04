package com.example.gameservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class BeanInfoConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void printBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        System.out.println("Beans in the application context:");
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
    }
}
