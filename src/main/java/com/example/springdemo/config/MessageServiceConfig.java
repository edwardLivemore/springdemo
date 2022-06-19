package com.example.springdemo.config;

import com.example.springdemo.service.MessageService;
import com.example.springdemo.service.impl.MessageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageServiceConfig {
    @Bean
    public MessageService getMessageService() {
        return new MessageServiceImpl();
    }
}
