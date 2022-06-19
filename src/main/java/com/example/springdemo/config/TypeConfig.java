package com.example.springdemo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.example.springdemo.type.Impl")
public class TypeConfig {
}
