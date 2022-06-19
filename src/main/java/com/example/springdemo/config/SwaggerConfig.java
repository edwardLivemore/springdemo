/*
 * @Author: your name
 * @Date: 2021-05-07 18:59:31
 * @LastEditTime: 2021-05-07 19:13:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \springdemo\src\main\java\com\example\springdemo\config\SwaggerConfig.java
 */
package com.example.springdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket customDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(appInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.example.springdemo.controller"))
                    .paths(PathSelectors.any())
                    .build();
    }

    private ApiInfo appInfo() {
        return new ApiInfoBuilder()
                    .title("测试API文档")
                    .version("1.0.0")
                    .build();
    }
}