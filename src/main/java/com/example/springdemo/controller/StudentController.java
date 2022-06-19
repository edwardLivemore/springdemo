package com.example.springdemo.controller;

import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/student")
@Api("学生信息管理功能")
@Slf4j
public class StudentController {
    @Autowired
    private Set<String> routers;

    @ApiOperation("测试方法")
    @GetMapping("/test")
    public void test() {
        Set<String> set = new HashSet<>();
        set.add("123");
        log.info(set.toString());
        log.info(routers.toString());
        log.info("this is a test method.");
    }
}