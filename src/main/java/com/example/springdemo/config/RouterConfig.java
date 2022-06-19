package com.example.springdemo.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class RouterConfig {
    @Bean
    public Set<String> getRouterConfig() {
        Set<String> routers = new HashSet<>();
        routers.add("/merchant/groupwork");
        routers.add("/merchant/activityaction?type=miaosha");
        routers.add("/merchant/agentlist");
        routers.add("/merchant/groupwork");
        routers.add("/merchant/agentlist");
        routers.add("/merchant/agentdata");
        routers.add("/merchant/live");
        return routers;
    }
}