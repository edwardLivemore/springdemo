package com.example.springdemo.service;

import com.example.springdemo.req.MessageReq;

public interface MessageService {
    void run();

    void addMessage(MessageReq req);
}
