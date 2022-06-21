package com.example.springdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "taskPool")
    public ThreadPoolExecutor getTaskThreadPool() {
        return new ThreadPoolExecutor(10, 50, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean(name = "sendPool")
    public ThreadPoolExecutor getSendThreadPool() {
        return new ThreadPoolExecutor(30, 50, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
