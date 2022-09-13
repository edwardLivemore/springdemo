package com.example.springdemo;

import com.example.springdemo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.Arrays;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringDemoApplication implements ApplicationRunner {
	@Autowired
	private MessageService messageService;

	@Autowired
	private String[] numberArray;

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(Arrays.toString(numberArray));
		messageService.run();
	}
}
