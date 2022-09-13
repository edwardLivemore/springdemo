package com.example.springdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArrayConfig {
    @Bean
    public String[] getNumberArray() {
        int count = 20;
        String[] array = new String[count];
        int start = 97;
        for (int i = 0; i < count; i++) {
            array[i] = String.valueOf((char) (start + i));
        }
        return array;
    }
}
