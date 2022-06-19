package com.example.springdemo.config;

import com.example.springdemo.type.ICustomType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class TypeRequiredConfig {
    @Bean
    public List<ICustomType> getCustomType(){
        List<ICustomType> list = new ArrayList<>();
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TypeConfig.class);
        Map<String, ICustomType> map = applicationContext.getBeansOfType(ICustomType.class);
        map.forEach((key, value) -> list.add(value));
        return list;
    }
}
