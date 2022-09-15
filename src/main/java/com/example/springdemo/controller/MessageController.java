package com.example.springdemo.controller;

import com.example.springdemo.req.MessageReq;
import com.example.springdemo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("message")
    public String addMessage(@RequestBody MessageReq req) {
        messageService.addMessage(req);
        return "success";
    }

    @GetMapping("message")
    public String showMessage() {
        messageService.showMessage();
        return "success";
    }
}
